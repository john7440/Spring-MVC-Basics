package com.example.web;

import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * Data Transfer Object representing a single item in the shopping cart
 */
@Data
public class CartItemDTO {
    private Long id;
    @Min(value = 1)
    private int quantity;
}
