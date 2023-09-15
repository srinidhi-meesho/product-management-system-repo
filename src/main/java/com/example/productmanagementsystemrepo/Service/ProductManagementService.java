package com.example.productmanagementsystemrepo.Service;

import com.example.productmanagementsystemrepo.Requests.AdminPriceRequests.PriceRequest;
import com.example.productmanagementsystemrepo.db.PriceDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductManagementService {
    @Autowired
    private final PriceDb priceDb;

    public ProductManagementService(PriceDb priceDb) {
        this.priceDb = priceDb;
    }

    public List<PriceRequest> getAllPriceUpdateRequests(LocalDateTime twentyFourHoursBack, LocalDateTime now, Integer batchSize, Integer offset) {
        return priceDb.priceUpdateRequests(twentyFourHoursBack, now, batchSize, offset);
    }
}
