package com.lopez.ecommerce_api.service.product_service;

import com.lopez.ecommerce_api.dto.RequestProduct;
import com.lopez.ecommerce_api.model.Cart;
import com.lopez.ecommerce_api.model.CartItem;
import com.lopez.ecommerce_api.model.Product;
import com.lopez.ecommerce_api.repository.CartItemRepository;
import com.lopez.ecommerce_api.repository.ProductRepository;
import com.lopez.ecommerce_api.service.user_service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepo;
    private final UserService userService;
    private final CartItemRepository cartItemRepo;

    @Override
    public Product saveProduct(RequestProduct request) {
        if(productRepo.existsByName(request.getName())) {
            return null;
        }
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .category(request.getCategory())
                .build();
        return productRepo.save(product);
    }

    @Override
    public Product getProduct(Long id) {
        return productRepo.findById(id).orElse(null);
    }

    @Override
    public Product updateProduct(RequestProduct request, Long id) {
        if(!productRepo.existsById(id)) {
            return null;
        }
        Product product = productRepo.findById(id).get(); // Use get() since it returns an Optional<Product>
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(request.getCategory());
        return product;
    }

    @Override
    public List<Product> getProducts() {
        return productRepo.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String categoryName) {
        return productRepo.findByCategory(categoryName);
    }

    @Override
    public boolean deleteProduct(Long id) {
        if(!productRepo.existsById(id)) {
            return false;
        }
        productRepo.deleteById(id);
        return true;
    }


}
