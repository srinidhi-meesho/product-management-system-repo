package com.example.productmanagementsystemrepo.Service;

import com.example.productmanagementsystemrepo.Dto.FinalMessage;
import com.example.productmanagementsystemrepo.Model.MeeshoProducts;
import com.example.productmanagementsystemrepo.Model.PriceUpdateRequests;
import com.example.productmanagementsystemrepo.Repository.PriceUpdateRepository;
import com.example.productmanagementsystemrepo.Repository.ProductRepository;
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
    private final PriceUpdateRepository priceUpdateRepository;
    private final ProductRepository productRepository;

    public PriceUpdateService(PriceUpdateRepository priceUpdateRepository, ProductRepository productRepository) {
        this.priceUpdateRepository = priceUpdateRepository;
        this.productRepository = productRepository;
    }

    //private static final Logger logger  = LoggerFactory.getLogger(PriceUpdateService.class);

    public ResponseEntity<FinalMessage> priceUpdate(List<String > strings, double delta) {
        PriceUpdateRequests priceUpdateRequests = new PriceUpdateRequests();
        List<String> newList = new ArrayList<>();
        for(String productId : strings) {
            String newProductId = productId.trim();
            newList.add(newProductId);
        }
        priceUpdateRequests.setProductIdList(newList.toString());
        priceUpdateRequests.setDelta(delta);
        priceUpdateRepository.save(priceUpdateRequests);
        return new ResponseEntity<>(new FinalMessage("SUCCESS","List saved successfully"), HttpStatus.CREATED);
    }


    public void priceUpdateReq(String  productId, double delta) {
        MeeshoProducts meeshoProducts = productRepository.findByProductId(productId);
        if(meeshoProducts == null) {
            throw new NoSuchElementException("no request to update");
        }
        else {
            meeshoProducts.setPrice(meeshoProducts.getPrice()+delta);
            productRepository.save(meeshoProducts);
//            logger.info("price update for the given productId {} updated sucessfully", productId);
            log.info("price update for the given productId {} updated sucessfully", productId);
        }
    }
}
