package com.pca.pokimages.repository;

// src/main/java/com/example/pokimages/repository/CardRepository.java

import com.pca.pokimages.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, String> {
}