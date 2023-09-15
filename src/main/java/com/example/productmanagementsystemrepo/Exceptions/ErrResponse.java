package com.example.productmanagementsystemrepo.Exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrResponse {
    private String code;
    private String message;
}
