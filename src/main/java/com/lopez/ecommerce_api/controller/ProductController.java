package com.lopez.ecommerce_api.controller;

import com.lopez.ecommerce_api.dto.RequestProduct;
import com.lopez.ecommerce_api.model.Product;
import com.lopez.ecommerce_api.service.productservice.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        return new ResponseEntity<>(productService.getProductsByCategory(category.toUpperCase()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Product product = productService.getProduct(id);
        if(product == null) {
            return new ResponseEntity<>("Product with ID: " + id + " does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> saveProduct(@RequestBody RequestProduct request) {
        Product product = productService.saveProduct(request);
        if(product == null) {
            return new ResponseEntity<>(Map.of("error", "Product with name " + request.getName() + " already exist."), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(Map.of("message", "Product Created"), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, String>> updateProduct(@RequestBody RequestProduct request,
                                                @PathVariable Long id) {
        Product product = productService.updateProduct(request, id);
        if(product == null) {
            return new ResponseEntity<>(Map.of("error", "Product with ID:" + id +  "does not exist"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(Map.of("message", "Product with ID:" + id + " has been updated"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteProductById(@PathVariable Long id) {
        if(!productService.deleteProduct(id)) {
            return new ResponseEntity<>(Map.of("error", "Product with ID:" + id +  "does not exist"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
