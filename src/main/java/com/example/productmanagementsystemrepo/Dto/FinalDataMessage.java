package com.example.productmanagementsystemrepo.Dto;

import lombok.Data;

@Data
public class FinalDataMessage {
    private FinalProducts data;

    public FinalDataMessage(FinalProducts data) {
        this.data = data;
    }
}
