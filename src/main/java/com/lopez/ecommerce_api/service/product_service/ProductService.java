package com.lopez.ecommerce_api.service.product_service;

import com.lopez.ecommerce_api.dto.RequestProduct;
import com.lopez.ecommerce_api.model.Product;

import java.util.List;

public interface ProductService {


    Product saveProduct(RequestProduct product);
    Product getProductById(Long id);
    Product getProductByName(String name);
    Product updateProduct(RequestProduct request, Long id);
    List<Product> getProducts();
    List<Product> getProductsByCategory(String categoryName);
    boolean deleteProduct(Long id);
    void updateProductStock(Product product, int quantity);

}

