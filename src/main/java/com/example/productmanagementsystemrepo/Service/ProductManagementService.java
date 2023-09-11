package com.example.productmanagementsystemrepo.Service;

import com.example.productmanagementsystemrepo.Model.PriceUpdateRequests;
import com.example.productmanagementsystemrepo.Repository.PriceUpdateRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductManagementService {
    private final PriceUpdateRepository priceUpdateRepository;

    public ProductManagementService(PriceUpdateRepository priceUpdateRepository) {
        this.priceUpdateRepository = priceUpdateRepository;
    }

    public List<PriceUpdateRequests> getAllPriceUpdateRequests(LocalDateTime twentyFourHoursBack, LocalDateTime now) {
        return priceUpdateRepository.findByCreatedAtBetween(twentyFourHoursBack, now);
    }
}
