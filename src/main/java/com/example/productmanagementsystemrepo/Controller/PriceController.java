package com.example.productmanagementsystemrepo.Controller;

import com.example.productmanagementsystemrepo.Responses.FinalMessage;
import com.example.productmanagementsystemrepo.Requests.AdminPriceRequests.ProductPriceUpdateRequest;
import com.example.productmanagementsystemrepo.Service.PriceUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/admin")
public class PriceController {
    @Autowired
    private final PriceUpdateService priceUpdateService;

    public PriceController(PriceUpdateService priceUpdateService) {
        this.priceUpdateService = priceUpdateService;
    }


    @PostMapping("/update-price-request")
    public ResponseEntity<FinalMessage> addPriceUpdate(@RequestBody @Valid ProductPriceUpdateRequest req) {
        return priceUpdateService.priceUpdate(req.getProductIdList(), req.getDelta());
    }
}
