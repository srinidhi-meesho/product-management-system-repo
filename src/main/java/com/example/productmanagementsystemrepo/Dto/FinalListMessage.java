package com.example.productmanagementsystemrepo.Dto;

import lombok.Data;

import java.util.List;

@Data
public class FinalListMessage {
    private List<FinalProducts> list;

    public FinalListMessage(List<FinalProducts> list) {
        this.list = list;
    }
}
