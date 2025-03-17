package com.pca.pokimages.model;

// src/main/java/com/example/pokimages/model/Card.java


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Card {
    @Id
    private String id;
    private String name;
    private String image;

    // Getters, setters, constructeurs
}