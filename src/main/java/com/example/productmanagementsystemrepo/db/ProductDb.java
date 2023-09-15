package com.example.productmanagementsystemrepo.db;

import com.example.productmanagementsystemrepo.Model.MeeshoProducts;
import com.example.productmanagementsystemrepo.Repository.ProductRepository;
import com.example.productmanagementsystemrepo.Requests.ProductRequests.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ProductDb {
    @Autowired
    private ProductRepository productRepository;

    public MeeshoProducts getProduct(String productId) {
        return productRepository.findByProductId(productId);
    }

    public List<MeeshoProducts> getProduct(Integer supplierId) {
        return productRepository.findBySupplierId(supplierId);
    }

    public MeeshoProducts getProduct(String productId, Integer supplierId) {
        return productRepository.findByProductIdAndSupplierId(productId, supplierId);
    }

    public void saveProduct(ProductRequest product){
        MeeshoProducts products = new MeeshoProducts();
        products.setProductId(product.getProductId());
        products.setSupplierId(product.getSupplierId());
        products.setName(product.getName());
        products.setPrice(product.getPrice());
        products.setDiscount(product.getDiscount());
        products.setImageUrl(product.getImageUrl());
        products.setInventory(product.getInventory());
        products.setCreatedAt(LocalDateTime.now());
        products.setUpdatedAt(LocalDateTime.now());
        productRepository.save(products);
    }
    public void save(MeeshoProducts products) {
         productRepository.save(products);
    }
    public void deleteProduct(String productId, Integer supplierId) {
        productRepository.deleteByProductIdAndSupplierId(productId, supplierId);
    }

}
