package com.example.productmanagementsystemrepo.Service;

import com.example.productmanagementsystemrepo.Clients.SupplierPaymentClient;
import com.example.productmanagementsystemrepo.Dto.*;
import com.example.productmanagementsystemrepo.Model.MeeshoProducts;
import com.example.productmanagementsystemrepo.Requests.ProductRequests.ProductRequest;
import com.example.productmanagementsystemrepo.Requests.ProductRequests.UpdateProductRequest;
import com.example.productmanagementsystemrepo.Responses.AllProductDetails;
import com.example.productmanagementsystemrepo.Responses.FinalMessage;
import com.example.productmanagementsystemrepo.Responses.FinalProducts;
import com.example.productmanagementsystemrepo.Responses.ProductDetails;
import com.example.productmanagementsystemrepo.db.ProductDb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private final ProductDb productDb;
    @Autowired
    private final SupplierPaymentClient supplierPaymentClient;

    @Value("${supplier-payment.auth-key}")
    private String authHeader;
    @Value("${supplier-payment.country-code}")
    private String countryCode;

    private Map<String,String> requestHeaders(String authHeader, String countryCode) {
        Map<String , String> headers = new HashMap<>();
        headers.put("Authorization", authHeader);
        headers.put("MEESHO-ISO-COUNTRY-CODE", countryCode);
        return headers;
    }

    public ProductService(ProductDb productDb, SupplierPaymentClient supplierPaymentClient) {
        this.productDb = productDb;
        this.supplierPaymentClient = supplierPaymentClient;
    }


    public FinalProducts FinalProductDetails(MeeshoProducts product) {
        FinalProducts products = new FinalProducts();
        products.setProductId(product.getProductId());
        products.setDiscount(product.getDiscount());
        products.setName(product.getName());
        products.setPrice(product.getPrice());
        products.setInventory(product.getInventory());
        products.setSupplierId(product.getSupplierId());
        products.setImageUrl(product.getImageUrl());
        try {
            Map<String, String> requestHeaders = requestHeaders(authHeader, countryCode);
            ResponseEntity<FinalPriceDto> finalPrice = supplierPaymentClient.getFinalTransferPrice(requestHeaders, new FinalPriceDto(product.getPrice()));
            if (finalPrice.getBody() != null)
                products.setFinalTransferPrice(finalPrice.getBody().getPrice());
        }catch (Exception e) {
            log.error("Error in Final Product details function: {}", e.getMessage());
        }
        return products;
    }

    public void ValidityCheck(String productId, Integer supplierId) {
        Boolean fl = false;
        for(int i=0;i<productId.length();i++) {
            char ch = productId.charAt(i);
            if(ch >= 'a' && ch<='z' || (ch>='0' && ch<='9') || ch=='_') {
                continue;
            } else {
                fl=true;
                break;
            }
        }
        if(fl && !(supplierId> 0)) {
            throw new IllegalArgumentException("productId can contain only alphanumeric characters and underscores,SupplierId should be positive integer");
        } else if (fl) {
            throw new IllegalArgumentException("productId can contain only alphanumeric characters and underscores");
        } else if (!(supplierId> 0)){
            throw new IllegalArgumentException("SupplierId should be positive integer");
        }
    }

    public ResponseEntity<FinalMessage> addProduct(ProductRequest product) {
        if(product == null) {
            throw new IllegalArgumentException("no product to add");
        }
        if(product.getInventory() == null) {
            product.setInventory(20);
        }
        MeeshoProducts existing = productDb.getProduct(product.getProductId());
        if(existing != null) {
            return new ResponseEntity<>(new FinalMessage("EXISTING", "Product already exists with the given productId"), HttpStatus.FOUND);
        } else {
           try {
               productDb.saveProduct(product);
               return new ResponseEntity<>(new FinalMessage("SUCCESS", "Product added successfully"), HttpStatus.CREATED);
           }catch (Exception e) {
               log.error("Error in adding the product details,{}", e.getMessage());
               return new ResponseEntity<>(new FinalMessage("FAILED", "Adding product details failed"), HttpStatus.INTERNAL_SERVER_ERROR);
           }
        }
    }

    public ResponseEntity<FinalMessage> updateProduct(Integer  supplierId, String  productId, UpdateProductRequest product) {
        ValidityCheck(productId, supplierId);
        if(product == null) {
            throw new IllegalArgumentException("product can't be empty");
        }
        MeeshoProducts existing = productDb.getProduct(productId, supplierId);
        if(existing == null) {
            throw new NoSuchElementException("no product with given product id and supplier id exists");
        } else {
           try {
               if (product.getName() != null) {
                   existing.setName(product.getName());
               }
               if (product.getDiscount() != null && product.getDiscount() >= 0 && product.getDiscount() <= 30) {
                   existing.setDiscount(product.getDiscount());
               }
               if (product.getPrice() != null && product.getPrice() >= 50.0 && product.getPrice() <= 2000.0) {
                   existing.setPrice(product.getPrice());
               }
               if (product.getImageUrl() != null) {
                   existing.setImageUrl(product.getImageUrl());
               }
               if (product.getInventory() != null) {
                   existing.setInventory(product.getInventory());
               }
               existing.setUpdatedAt(LocalDateTime.now());
               productDb.save(existing);
               return new ResponseEntity<>(new FinalMessage("SUCCESS", "Product details updated successfully"), HttpStatus.OK);
           }catch (Exception e) {
               log.error("Error in updating the product details for productId : {},{}", productId, e.getMessage());
               return new ResponseEntity<>(new FinalMessage("FAILED","Product details updation failed"), HttpStatus.INTERNAL_SERVER_ERROR);
           }
        }
    }

    public ResponseEntity<FinalMessage> deleteProduct(Integer  supplierId, String  productId) {
        ValidityCheck(productId, supplierId);
        MeeshoProducts existing = productDb.getProduct(productId, supplierId);
        if(existing == null) {
            throw new NoSuchElementException("no product with given product id and supplier id exists");
        } else {
            try {
                productDb.deleteProduct(productId, supplierId);
                return new ResponseEntity<>(new FinalMessage("SUCCESS", "product deletion success"), HttpStatus.OK);
            }catch(Exception e) {
                log.error("Error in deleting the product of productId: {}, {}",productId, e.getMessage());
                return new ResponseEntity<>(new FinalMessage("FAILED", "Product deletion failed"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
    public ResponseEntity<ProductDetails> getProductDetails(String productId) {
        for(int i=0;i<productId.length();i++) {
            char ch = productId.charAt(i);
            if(ch >= 'a' && ch<='z' || (ch>='0' && ch<='9') || ch=='_') {
                continue;
            } else {
                throw new IllegalArgumentException("productId can contain only alphanumeric characters and underscores");
            }
        }
        MeeshoProducts product = productDb.getProduct(productId);
        if(product == null) {
            throw new NoSuchElementException("no product with given productId exists");
        } else {
            try {
                FinalProducts products= FinalProductDetails(product);
                return new ResponseEntity<>(new ProductDetails(products), HttpStatus.OK);
            }catch (Exception e) {
                log.error("Error in getting the productDetails of the product of productId : {},{}", productId, e.getMessage());
                return new ResponseEntity<>(new ProductDetails(null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    public ResponseEntity<AllProductDetails> getAllProducts(Integer  supplierId) {
        if(supplierId < 0) {
            throw new IllegalArgumentException("SupplierId can't be negative");
        }
        List<MeeshoProducts> list = productDb.getProduct(supplierId);
        if(list.isEmpty()) {
            throw new NoSuchElementException("no products with given supplier id exists");
        } else {
           try {
               List<FinalProducts> finalProducts = new ArrayList<>();
               for(MeeshoProducts product : list) {
                   FinalProducts products = FinalProductDetails(product);
                   finalProducts.add(products);
               }
               return new ResponseEntity<>(new AllProductDetails(finalProducts), HttpStatus.OK);
           }catch (Exception e) {
               log.error("Error in getting the product details of the supplierId : {}, {}", supplierId, e.getMessage());
               return new ResponseEntity<>(new AllProductDetails(null), HttpStatus.INTERNAL_SERVER_ERROR);
           }
        }
    }
}
