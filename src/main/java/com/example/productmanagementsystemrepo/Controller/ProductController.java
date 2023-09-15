package com.example.productmanagementsystemrepo.Controller;

import com.example.productmanagementsystemrepo.Responses.ProductDetails;
import com.example.productmanagementsystemrepo.Responses.AllProductDetails;
import com.example.productmanagementsystemrepo.Responses.FinalMessage;
import com.example.productmanagementsystemrepo.Requests.ProductRequests.ProductRequest;
import com.example.productmanagementsystemrepo.Requests.ProductRequests.UpdateProductRequest;
import com.example.productmanagementsystemrepo.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1")
@Validated
public class ProductController {
   @Autowired
   private ProductService productService;
    public ProductController(ProductService productService) {

        this.productService = productService;
    }
    @PostMapping("/product/add")
    public ResponseEntity<FinalMessage> addProduct(@Valid @RequestBody ProductRequest product) {
        return productService.addProduct(product);
    }

    @PutMapping("/product/update/{supplierId}/{productId}")
    public ResponseEntity<FinalMessage> updateProduct(@PathVariable Integer supplierId, @PathVariable String productId, @Valid @RequestBody UpdateProductRequest product) {
        return productService.updateProduct(supplierId, productId, product);
    }

    @DeleteMapping("/product/{supplierId}/{productId}")
    public ResponseEntity<FinalMessage> deleteProduct(@PathVariable Integer supplierId, @PathVariable String productId) {
        return productService.deleteProduct(supplierId, productId);
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDetails> getProductDetails(@PathVariable String productId) {
        return productService.getProductDetails(productId);
    }

    @GetMapping("/all-products/supplier/{supplierId}")
    public ResponseEntity<AllProductDetails> getAllProducts(@PathVariable Integer supplierId) {
        return productService.getAllProducts(supplierId);
    }
}
