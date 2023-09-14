package com.example.productmanagementsystemrepo.Requests.ProductRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {
    @NotNull(message = "name is required")
    @Pattern(regexp = "^[^\\s]+$", message = "Name should not contain spaces")
    @Column(name = "name")
    private String name;
    @NotNull(message = "imageUrl is required")
    @Pattern(regexp = "^[^\\s]+$", message = "imageUrl should not contain spaces")
    @Column(name = "image_url")
    private String imageUrl;
    @Positive(message = "price can't be negative")
    @DecimalMin(value = "50.0", message = "minimum value of price should be 50.0")
    @DecimalMax(value = "2000.0", message = "maximum value of price is 2000.0")
    @Column(name = "price")
    private Double price;
    @Getter
    @Min(value = 0, message = "minimum value of discount is 0")
    @Max(value = 30, message = "maximum value of discount is 30")
    @Column(name = "discount")
    private Integer discount;
    @Getter
    @Column(name = "inventory")
    private Integer inventory;
}
