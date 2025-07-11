package com.lopez.ecommerce_api.service.productservice;

import com.lopez.ecommerce_api.dto.RequestProduct;
import com.lopez.ecommerce_api.model.Product;
import com.lopez.ecommerce_api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService{

    private final ProductRepository repository;

    @Override
    public Product saveProduct(RequestProduct request) {
        if(repository.existsByName(request.getName())) {
            return null;
        }
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .category(request.getCategory())
                .build();
        return repository.save(product);
    }

    @Override
    public Product getProduct(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Product updateProduct(RequestProduct request, Long id) {
        if(!repository.existsById(id)) {
            return null;
        }
        Product product = repository.findById(id).get(); // Use get() since it returns an Optional<Product>
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(request.getCategory());
        return product;
    }

    @Override
    public List<Product> getProducts() {
        return repository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String categoryName) {
        return repository.findByCategory(categoryName);
    }

    @Override
    public boolean deleteProduct(Long id) {
        if(!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

}
