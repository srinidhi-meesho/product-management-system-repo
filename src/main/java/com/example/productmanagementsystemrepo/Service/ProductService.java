package com.example.productmanagementsystemrepo.Service;

import com.example.productmanagementsystemrepo.Clients.SupplierPaymentClient;
import com.example.productmanagementsystemrepo.Dto.*;
import com.example.productmanagementsystemrepo.Model.MeeshoProducts;
import com.example.productmanagementsystemrepo.Repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final SupplierPaymentClient supplierPaymentClient;

    @Value("${authHeader}")
    private String authHeader;
    @Value("${countryCode}")
    private String countryCode;

    public ProductService(ProductRepository productRepository, SupplierPaymentClient supplierPaymentClient) {
        this.productRepository = productRepository;
        this.supplierPaymentClient = supplierPaymentClient;
    }


    public FinalProducts function(MeeshoProducts product) {
        FinalProducts products = new FinalProducts();
        products.setProductId(product.getProductId());
        products.setDiscount(product.getDiscount());
        products.setName(product.getName());
        products.setPrice(product.getPrice());
        products.setInventory(product.getInventory());
        products.setCreatedAt(product.getCreatedAt());
        products.setUpdatedAt(product.getUpdatedAt());
        products.setSupplierId(product.getSupplierId());
        products.setImageUrl(product.getImageUrl());
        try {
            ResponseEntity<FinalPriceDto> finalPrice = supplierPaymentClient.getFinalTransferPrice(authHeader, countryCode, new FinalPriceDto(product.getPrice()));
            if (finalPrice.getBody() != null)
                products.setFinalTransferPrice(finalPrice.getBody().getPrice());
            else throw new NullPointerException("final price is null");
        } catch (Exception e) {
            log.error("getting error in response entity: {}", e.getMessage());
        }
        return products;
    }

    public ResponseEntity<FinalMessage> addProduct(MeeshoProducts product) {
        if(product == null) {
            throw new IllegalArgumentException("no product to add");
        }
        if(product.getInventory() == null) {
            product.setInventory("20");
        }
        MeeshoProducts existing = productRepository.findByProductId(product.getProductId());
        if(existing != null) {
            return new ResponseEntity<>(new FinalMessage("EXISTING", "product already exists with given productId"), HttpStatus.FOUND);
        }
        else
        {
            productRepository.save(product);
            return new ResponseEntity<>(new FinalMessage("SUCCESS", "product creation success"), HttpStatus.CREATED);
        }
    }

    public ResponseEntity<FinalMessage> updateProduct(int  supplierId, String  productId, MeeshoProducts product) {
        if(product == null) {
            throw new IllegalArgumentException("product can't be empty");
        }
        MeeshoProducts existing = productRepository.findByProductIdAndSupplierId(productId, supplierId);
        if(existing == null) {
            throw new NoSuchElementException("no product with given product id and supplier id exists");
        }
        else {
            if (!product.getName().isEmpty())
                existing.setName(product.getName());
            else throw new IllegalArgumentException("name should not be empty");
            if (product.getDiscount() >= 0 && product.getDiscount() <= 30)
                existing.setDiscount(product.getDiscount());
            else throw new IllegalArgumentException("discount should be in the range 0 to 30");
            if (product.getPrice() >= 50.0 && product.getPrice() <= 2000.0)
                existing.setPrice(product.getPrice());
            else if (product.getPrice() < 0) {
                throw new IllegalArgumentException("price can't be negative");
            } else throw new IllegalArgumentException("price should be in the range 50.0 to 2000.0");
            if (!product.getImageUrl().isEmpty())
                existing.setImageUrl(product.getImageUrl());
            else throw new IllegalArgumentException("image_url should not be empty");
            if (!product.getInventory().isEmpty())
                existing.setInventory(product.getInventory());
            else throw new IllegalArgumentException("inventory can't be empty");
            productRepository.save(existing);
            return new ResponseEntity<>(new FinalMessage("SUCCESS", "product updation success"), HttpStatus.OK);
        }
    }

    public ResponseEntity<FinalMessage> deleteProduct(int  supplierId, String  productId) {
        MeeshoProducts existing = productRepository.findByProductIdAndSupplierId(productId, supplierId);
        if(existing == null) {
            throw new NoSuchElementException("no product with given product id and supplier id exists");
        }
        else {
            try {
                productRepository.deleteByProductIdAndSupplierId(productId, supplierId);
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
            return new ResponseEntity<>(new FinalMessage("SUCCESS", "product deletion success"), HttpStatus.OK);
        }
    }
    public ResponseEntity<FinalDataMessage> getProductDetails(String productId) {
        MeeshoProducts product = productRepository.findByProductId(productId);
        if(product == null) {
            throw new NoSuchElementException("no product with given productId exists");
        }
        else
        {
            FinalProducts products= function(product);
            return new ResponseEntity<>(new FinalDataMessage(products), HttpStatus.OK);
        }
    }

    public ResponseEntity<FinalListMessage> getAllProducts(int  supplierId) {
        List<MeeshoProducts> list = productRepository.findBySupplierId(supplierId);
        if(list.isEmpty()) {
            throw new NoSuchElementException("no products with given supplier id exists");
        }
        else
        {
            List<FinalProducts> finalProducts = new ArrayList<>();
            for(MeeshoProducts product : list) {
               FinalProducts products = function(product);
                finalProducts.add(products);
            }
            return new ResponseEntity<>(new FinalListMessage(finalProducts), HttpStatus.OK);
        }
    }

}
