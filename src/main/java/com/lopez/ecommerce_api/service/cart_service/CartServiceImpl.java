package com.lopez.ecommerce_api.service.cart_service;

import com.lopez.ecommerce_api.dto.RequestProduct;
import com.lopez.ecommerce_api.model.Cart;
import com.lopez.ecommerce_api.model.CartItem;
import com.lopez.ecommerce_api.model.Product;
import com.lopez.ecommerce_api.model.User;
import com.lopez.ecommerce_api.repository.CartItemRepository;
import com.lopez.ecommerce_api.repository.CartRepository;
import com.lopez.ecommerce_api.repository.ProductRepository;
import com.lopez.ecommerce_api.service.user_service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService{

    private final ProductRepository productRepo;
    private final UserService userService;
    private final CartItemRepository cartItemRepo;
    private final CartRepository cartRepo;

    @Override
    public boolean addProductToCart(RequestProduct request, String username) {
        Product product = productRepo.findByName(request.getName());
        if(product.getStockQuantity() == 0) {
            return false;
        }
        Cart cart = getCart(username);
        cartItemRepo.save(new CartItem(null, product, cart, request.getQuantity()));
        return true;

    }

    @Override
    public void addCartToUser(String username) {
        User user = userService.findByUsername(username);
        cartRepo.save(new Cart(null, user, 0.0));
    }

    @Override
    public Cart getCart(String username) {
        return userService.findByUsername(username).getCart();
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemRepo.deleteById(id);
    }
}
