package com.example.productmanagementsystemrepo.Requests.AdminPriceRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPriceUpdateRequest {
    private List<String > productIdList;
    private Double delta;

}
