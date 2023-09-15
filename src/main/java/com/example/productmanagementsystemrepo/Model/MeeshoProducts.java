package com.example.productmanagementsystemrepo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "meesho_products")
public class MeeshoProducts {
    @Id
    @NotNull(message = "productId is required")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "productId can contain only alphanumeric characters and underscores")
    @Column(name = "product_id")
    private String productId;
    @NotNull(message = "supplierId is required")
    @Positive(message = "supplierId can't be negative")
    @Column(name = "supplier_id")
    private Integer supplierId;
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
    @NotNull(message = "price is required")
    @Column(name = "price")
    private Double price;
    @Getter
    @Min(value = 0, message = "minimum value of discount is 0")
    @Max(value = 30, message = "maximum value of discount is 30")
    @Column(name = "discount")
    @Positive(message = "discount can't be negative")
    private Integer discount;
    @Getter
    @Column(name = "inventory")
    @Positive(message = "inventory can't be negative")
    private Integer inventory;
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private  LocalDateTime updatedAt;


    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
