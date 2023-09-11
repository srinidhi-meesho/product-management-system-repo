package com.example.productmanagementsystemrepo.Controller;

import com.example.productmanagementsystemrepo.Dto.FinalDataMessage;
import com.example.productmanagementsystemrepo.Dto.FinalListMessage;
import com.example.productmanagementsystemrepo.Dto.FinalMessage;
import com.example.productmanagementsystemrepo.Model.MeeshoProducts;
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
    public ResponseEntity<FinalMessage> addProduct(@Valid @RequestBody MeeshoProducts product) {
        return productService.addProduct(product);
    }

    @PutMapping("/product/update/{supplierId}/{productId}")
    public ResponseEntity<FinalMessage> updateProduct(@PathVariable int supplierId, @PathVariable String productId, @Valid @RequestBody MeeshoProducts product) {
        return productService.updateProduct(supplierId, productId, product);
    }

    @DeleteMapping("/product/{supplierId}/{productId}")
    public ResponseEntity<FinalMessage> deleteProduct(@PathVariable int supplierId, @PathVariable String productId) {
        return productService.deleteProduct(supplierId, productId);
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<FinalDataMessage> getProductDetails(@PathVariable String productId) {
        return productService.getProductDetails(productId);
    }

    @GetMapping("/all_products/supplier/{supplierId}")
    public ResponseEntity<FinalListMessage> getAllProducts(@PathVariable int supplierId) {
        return productService.getAllProducts(supplierId);
    }
}
