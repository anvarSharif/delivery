package com.example.delivery.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@SuperBuilder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Integer price;
    private String name;
    @ManyToOne
    private Category category;
    @ManyToOne
    private Attachment photo;

    public Product() {
        super();
    }
}
