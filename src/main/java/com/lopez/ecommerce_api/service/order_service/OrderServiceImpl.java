package com.lopez.ecommerce_api.service.order_service;

import com.lopez.ecommerce_api.dto.ResponseOrder;
import com.lopez.ecommerce_api.enums.CartItemStatus;
import com.lopez.ecommerce_api.enums.OrderStatus;
import com.lopez.ecommerce_api.enums.PaymentStatus;
import com.lopez.ecommerce_api.model.Cart;
import com.lopez.ecommerce_api.model.CustomerOrder;
import com.lopez.ecommerce_api.model.OrderItem;
import com.lopez.ecommerce_api.repository.OrderRepository;
import com.lopez.ecommerce_api.service.cart_service.CartService;
import com.lopez.ecommerce_api.service.product_service.ProductService;
import com.lopez.ecommerce_api.service.user_service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepo;
    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;

    public void saveOrder(Principal principal) {
        Cart cart = cartService.getCartByUsername(principal.getName());
        CustomerOrder order = createCustomerOrderFromCart(cart);
        processSelectedCartItems(cart, order);

        orderRepo.save(order);
    }

    @Override
    public List<ResponseOrder> getUserOrders(String username) {
        return ResponseOrder.toListOfDto(userService.getUser(username).getOrders());
    }

    @Override
    public ResponseOrder getUserOrder(Long id) {
        return ResponseOrder.toDto(orderRepo.findById(id).get());
    }

    //Map Cart to CustomerOrder
    private CustomerOrder createCustomerOrderFromCart(Cart cart) {
        return CustomerOrder.builder()
                .user(cart.getUser())
                .totalPrice(0.0)
                .orderStatus(OrderStatus.CONFIRMED)
                .paymentStatus(PaymentStatus.PAID)
                .orderItems(new ArrayList<>())
                .build();
    }

    //add each OrderItem to CustomerOrder
    private void processSelectedCartItems(Cart cart, CustomerOrder order) {
        cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getStatus() == CartItemStatus.SELECTED) // only process selected cart items
                .forEach(cartItem -> {
                    productService.updateProductStock(cartItem.getProduct(), cartItem.getQuantity());
                    OrderItem orderItem = OrderItem.builder()
                            .product(cartItem.getProduct())
                            .quantity(cartItem.getQuantity())
                            .price(cartItem.getProduct().getPrice())
                            .build();
                    order.addOrderItem(orderItem);
                    //delete cartItem from cart if successfully ordered
                    cartService.deleteCartItem(cartItem.getId());
                });
    }

}
