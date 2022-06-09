package com.hamsoft.abc_ecommerce.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "categories")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank
    @Column(name = "category_name")
    public String categoryName;

    @NotBlank
    public String description;

    @NotBlank
    public String imageUrl;

}
