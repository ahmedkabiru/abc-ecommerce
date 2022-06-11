package com.hamsoft.abc_ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String name;
    private  String imageURL;
    private  double price;
    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    Category category;

}
