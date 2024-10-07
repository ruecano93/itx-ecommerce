package com.itx.core.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "sku")
    private String sku;

    @Column(name = "join_life")
    private String joinLife;

    @Column(name = "collection")
    private String collection;

    @Column(name = "material")
    private String material;

    @OneToMany(mappedBy = "price", fetch = FetchType.LAZY)
    private List<Price> prices;
}
