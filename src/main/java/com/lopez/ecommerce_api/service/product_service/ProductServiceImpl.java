package com.lopez.ecommerce_api.service.product_service;

import com.lopez.ecommerce_api.dto.RequestProduct;
import com.lopez.ecommerce_api.model.Product;
import com.lopez.ecommerce_api.repository.ProductRepository;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepo;

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
    public Product getProductByName(String name) {
        return productRepo.findByName(name);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepo.findById(id).orElse(null);
    }

    @Override
    public Product updateProduct(RequestProduct request, Long id) {
        if(!productRepo.existsById(id)) {
            return null;
        }
        Product product = productRepo.findById(id).get(); // success since we checked already if it exists
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

    @Retryable(
            value = OptimisticLockException.class,  // Retry on this exception
            maxAttempts = 3,                       // Max retries
            backoff = @Backoff(delay = 20)        // Delay between retries (ms)
    )
    @Override
    public void updateProductStock(Product product, int quantity) {
        int updatedStock = product.getStockQuantity() - quantity;
            product.setStockQuantity(updatedStock);

    }
}
