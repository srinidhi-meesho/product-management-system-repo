package com.example.productmanagementsystemrepo.Requests.ProductRequests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotNull(message = "productId is required")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "productId can contain only alphanumeric characters and underscores")
    private String productId;

    @NotNull(message = "supplierId is required")
    @Positive(message = "supplierId can't be negative")
    private Integer supplierId;

    @NotNull(message = "name is required")
    @Pattern(regexp = "^[^\\s]+$", message = "Name should not contain spaces")
    private String name;

    @NotNull(message = "imageUrl is required")
    @Pattern(regexp = "^[^\\s]+$", message = "imageUrl should not contain spaces")
    private String imageUrl;

    @Positive(message = "price can't be negative")
    @NotNull(message = "price is required")
    @DecimalMin(value = "50.0", message = "minimum value of price should be 50.0")
    @DecimalMax(value = "2000.0", message = "maximum value of price is 2000.0")
    private Double price;

    @Min(value = 0, message = "minimum value of discount is 0")
    @Max(value = 30, message = "maximum value of discount is 30")
    private Integer discount;

    private Integer inventory;

}
