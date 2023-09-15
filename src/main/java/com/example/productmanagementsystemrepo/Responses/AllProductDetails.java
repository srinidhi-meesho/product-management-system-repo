package com.example.productmanagementsystemrepo.Responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AllProductDetails {
    private List<FinalProducts> list;
}
