package com.example.productmanagementsystemrepo.Requests.AdminPriceRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceRequest {
    private Integer id;

    private String productIdList;

    private Double delta;

    private LocalDateTime createdAt;

    private  LocalDateTime updatedAt;
}
