package com.example.productmanagementsystemrepo.Clients;

import com.example.productmanagementsystemrepo.Constants.Constants;
import com.example.productmanagementsystemrepo.Dto.FinalPriceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;
import java.util.Map;


@FeignClient(name = Constants.SupplierPayment.NAME,  url = Constants.SupplierPayment.URL)
public interface SupplierPaymentClient {
    @PostMapping(value = "api/v2/supplier-payments/final-transfer-price", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<FinalPriceDto> getFinalTransferPrice(@RequestHeader Map<String, String> requestHeaders, @Valid @RequestBody FinalPriceDto finalPriceDto);
}
