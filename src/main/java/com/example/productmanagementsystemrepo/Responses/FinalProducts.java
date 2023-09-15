package com.example.productmanagementsystemrepo.Responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinalProducts {
    private String productId;

    private Integer supplierId;

    private String name;

    private String imageUrl;

    private Double price;

    private Integer discount;

    private Integer inventory;

    private Double finalTransferPrice;
}
