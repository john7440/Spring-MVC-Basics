package com.example.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data @NoArgsConstructor
@AllArgsConstructor
public class Article implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 5, max = 50)
    private String brand;

    @NotNull
    @Size(min = 5, max = 50)
    private String model;

    private String description;

    @NotNull
    @DecimalMin("1")
    private double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Article(String brand, String model, String description, double price) {
        this.brand = brand;
        this.model = model;
        this.description = description;
        this.price = price;
    }

    public Article(String brand, String model, String description, double price, Category category) {
        this.brand = brand;
        this.model = model;
        this.description = description;
        this.price = price;
        this.category = category;
    }

}
