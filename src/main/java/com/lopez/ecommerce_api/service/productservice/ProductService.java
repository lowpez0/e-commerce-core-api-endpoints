package com.lopez.ecommerce_api.service.productservice;

import com.lopez.ecommerce_api.dto.RequestProduct;
import com.lopez.ecommerce_api.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {


    Product saveProduct(RequestProduct product);
    Product getProduct(Long id);
    Product updateProduct(RequestProduct request, Long id);
    List<Product> getProducts();
    List<Product> getProductsByCategory(String categoryName);
    boolean deleteProduct(Long id);

}

