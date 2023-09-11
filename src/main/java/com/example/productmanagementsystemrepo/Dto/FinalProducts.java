package com.example.productmanagementsystemrepo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinalProducts {
    private String productId;

    private int supplierId;

    private String name;

    private String imageUrl;

    private double price;

    private int discount;

    private String inventory;

    private LocalDateTime createdAt;

    private  LocalDateTime updatedAt;

    private double finalTransferPrice;

}
