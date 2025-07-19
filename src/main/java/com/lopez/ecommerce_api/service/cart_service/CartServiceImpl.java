package com.lopez.ecommerce_api.service.cart_service;

import com.lopez.ecommerce_api.dto.RequestCart;
import com.lopez.ecommerce_api.dto.RequestProduct;
import com.lopez.ecommerce_api.dto.ResponseCart;
import com.lopez.ecommerce_api.enums.CartItemStatus;
import com.lopez.ecommerce_api.exception.OutOfStockException;
import com.lopez.ecommerce_api.model.Cart;
import com.lopez.ecommerce_api.model.CartItem;
import com.lopez.ecommerce_api.model.Product;
import com.lopez.ecommerce_api.model.User;
import com.lopez.ecommerce_api.repository.CartItemRepository;
import com.lopez.ecommerce_api.repository.CartRepository;
import com.lopez.ecommerce_api.service.product_service.ProductService;
import com.lopez.ecommerce_api.service.user_service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService{

    private final ProductService productService;
    private final UserService userService;
    private final CartItemRepository cartItemRepo;
    private final CartRepository cartRepo;

    @Override
    public void addCartItemToCart(RequestProduct request, String username) throws OutOfStockException {
        Product product = productService.getProductByName(request.getName());
        if(product == null || product.getStockQuantity() < request.getQuantity()) {
            throw new OutOfStockException(product.getName(), product.getStockQuantity(), request.getQuantity());
        }
        Cart cart = getCartByUsername(username);
        cartItemRepo.save(new CartItem(null, product, cart, request.getQuantity(), CartItemStatus.PENDING));
    }

    @Override
    public void addCartToUser(String username) {
        User user = userService.findByUsername(username);
        cartRepo.save(new Cart(null, user));
    }

    @Override
    public ResponseCart getCart(String username) {
        Cart cart = userService.findByUsername(username).getCart();
        return ResponseCart.toDto(cart);
    }

    @Override
    public Cart getCartByUsername(String username) {
        return userService.findByUsername(username).getCart();
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemRepo.deleteById(id);
    }

    @Override
    public void updateCartItems(RequestCart requestCart) throws OutOfStockException {
        for(RequestCart.CartItems item: requestCart.cart()) {
            CartItem cartItem = cartItemRepo.findById(item.cartItemId()).get();
            int updatedStock = cartItem.getProduct().getStockQuantity() - item.quantity();
            if(updatedStock < 0) {
                throw new OutOfStockException(cartItem.getProduct().getName(), cartItem.getProduct().getStockQuantity(), item.quantity());
            }
            cartItem.setQuantity(item.quantity());
            cartItem.setStatus(CartItemStatus.SELECTED);
        }

    }
}
