package com.example.productmanagementsystemrepo.Clients;

import com.example.productmanagementsystemrepo.Dto.FinalPriceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;


@FeignClient(name = "supplierPayment", url = "http://localhost:9141")
public interface SupplierPaymentClient {
    @PostMapping(value = "/api/v1/supplier-payments/v2/final-transfer-price", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<FinalPriceDto> getFinalTransferPrice(@RequestHeader("Authorization")String authHeader, @RequestHeader("MEESHO-ISO-COUNTRY-CODE") String countryCode, @Valid @RequestBody FinalPriceDto finalPriceDto);
}
