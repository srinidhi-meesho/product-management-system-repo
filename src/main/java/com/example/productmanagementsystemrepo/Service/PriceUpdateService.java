package com.example.productmanagementsystemrepo.Service;

import com.example.productmanagementsystemrepo.Responses.FinalMessage;
import com.example.productmanagementsystemrepo.Model.MeeshoProducts;
import com.example.productmanagementsystemrepo.Model.PriceUpdateRequest;
import com.example.productmanagementsystemrepo.db.PriceDb;
import com.example.productmanagementsystemrepo.db.ProductDb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class PriceUpdateService {
    @Autowired
    private final PriceDb priceDb;
    @Autowired
    private final ProductDb productDb;

    public PriceUpdateService(PriceDb priceDb, ProductDb productDb) {
        this.priceDb = priceDb;
        this.productDb = productDb;
    }

    //private static final Logger logger  = LoggerFactory.getLogger(PriceUpdateService.class);

    public ResponseEntity<FinalMessage> priceUpdate(List<String> productIds, Double delta) {
        try {
            PriceUpdateRequest priceUpdateRequest = new PriceUpdateRequest();
            List<String> newList = new ArrayList<>();
            for(String productId : productIds) {
                String newProductId = productId.trim();
                newList.add(newProductId);
            }
            priceUpdateRequest.setProductIdList(newList.toString());
            priceUpdateRequest.setDelta(delta);
            priceDb.savePriceRequest(priceUpdateRequest);
            return new ResponseEntity<>(new FinalMessage("SUCCESS","ProductId list & delta added successfully"), HttpStatus.CREATED);
        }catch(Exception e) {
            log.error("error in price update: {}", e.getMessage());
        }
        return new ResponseEntity<>(new FinalMessage("FAILED","Addition of ProductId list & delta failed"), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    public void priceUpdateReq(String  productId, Double delta) {
        MeeshoProducts meeshoProducts = productDb.getProduct(productId);
        if(meeshoProducts == null) {
            throw new NoSuchElementException("no product to update the price");
        } else {
           try {
               meeshoProducts.setPrice(meeshoProducts.getPrice()+delta);
               productDb.save(meeshoProducts);
//            logger.info("price update for the given productId {} updated sucessfully", productId);
               log.info("price update for the given productId {} updated sucessfully", productId);
           }catch (Exception e) {
               log.error("price updation failed for productId: {},{}", productId, e.getMessage());
           }
        }
    }
}
