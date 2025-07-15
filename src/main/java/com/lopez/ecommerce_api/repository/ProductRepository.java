package com.lopez.ecommerce_api.repository;

import com.lopez.ecommerce_api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByCategory(String category);
    boolean existsById(Long id);
    boolean existsByName(String name);
    Optional<Product> findById(Long id);
    Product findByName(String productName);
}
