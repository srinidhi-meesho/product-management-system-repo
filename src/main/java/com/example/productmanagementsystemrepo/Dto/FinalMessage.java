package com.example.productmanagementsystemrepo.Dto;

import lombok.Data;

@Data
public class FinalMessage {
    private  String code;
    private String message;

    public FinalMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
