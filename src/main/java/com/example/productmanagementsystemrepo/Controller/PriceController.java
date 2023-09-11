package com.example.productmanagementsystemrepo.Controller;

import com.example.productmanagementsystemrepo.Dto.FinalMessage;
import com.example.productmanagementsystemrepo.Dto.StringListReq;
import com.example.productmanagementsystemrepo.Service.PriceUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/product")
public class PriceController {
    @Autowired
    private final PriceUpdateService priceUpdateService;

    public PriceController(PriceUpdateService priceUpdateService) {
        this.priceUpdateService = priceUpdateService;
    }


    @PostMapping("/addProductIdList")
    public ResponseEntity<FinalMessage> addPriceUpdate(@RequestBody StringListReq req) {
        return priceUpdateService.priceUpdate(req.getProductIdList(), req.getDelta());
    }
}
