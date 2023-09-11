package com.example.productmanagementsystemrepo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StringListReq {
    private List<String > productIdList;
    private double delta;

}
