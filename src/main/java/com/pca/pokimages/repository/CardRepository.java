package com.pca.pokimages.repository;

import com.pca.pokimages.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CardRepository extends JpaRepository<Card, String> {
    List<Card> findBySetId(String setId);
}