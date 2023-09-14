package com.example.productmanagementsystemrepo.db;

import com.example.productmanagementsystemrepo.Model.PriceUpdateRequest;
import com.example.productmanagementsystemrepo.Repository.PriceUpdateRepository;
import com.example.productmanagementsystemrepo.Requests.AdminPriceRequests.PriceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Component
public class PriceDb {
    @Autowired
    private PriceUpdateRepository priceUpdateRepository;

    public List<PriceRequest> priceUpdateRequests(LocalDateTime twentyFourHoursBack, LocalDateTime now, Integer batchSize, Integer offset) {
        List<PriceUpdateRequest> priceUpdateRequest = priceUpdateRepository.findByCreatedAtBetween(twentyFourHoursBack, now, batchSize, offset);
        List<PriceRequest> priceRequests = new ArrayList<>();
        for(PriceUpdateRequest request : priceUpdateRequest) {
            PriceRequest pricereq = new PriceRequest();
            pricereq.setProductIdList(request.getProductIdList());
            pricereq.setDelta(request.getDelta());
            pricereq.setUpdatedAt(request.getUpdatedAt());
            pricereq.setCreatedAt(request.getCreatedAt());
            pricereq.setId(request.getId());
            priceRequests.add(pricereq);
        }
        return priceRequests;
    }
    public void savePriceRequest(List<PriceRequest> priceRequests) {
        List<PriceUpdateRequest> priceUpdateRequests = new ArrayList<>();
        for(PriceRequest requests : priceRequests) {
            PriceUpdateRequest priceUpdateRequest = new PriceUpdateRequest();
            priceUpdateRequest.setProductIdList(requests.getProductIdList());
            priceUpdateRequest.setDelta(requests.getDelta());
            priceUpdateRequest.setId(requests.getId());
            priceUpdateRequest.setUpdatedAt(requests.getUpdatedAt());
            priceUpdateRequest.setCreatedAt(requests.getCreatedAt());
            priceUpdateRequest.setId(requests.getId());
            priceUpdateRequests.add(priceUpdateRequest);
        }
        priceUpdateRepository.saveAll(priceUpdateRequests);
    }

    public void savePriceRequest(PriceUpdateRequest priceRequests) {
            priceUpdateRepository.save(priceRequests);
    }
}
