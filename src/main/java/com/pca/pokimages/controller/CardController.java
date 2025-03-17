package com.pca.pokimages.controller;

// src/main/java/com/example/pokimages/controller/CardController.java

import com.pca.pokimages.client.PokeApiClient;
import com.pca.pokimages.model.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private PokeApiClient pokeApiClient;



    @GetMapping("/{id}")
    public String getCardById(@PathVariable String id) {
        return pokeApiClient.getPokemonById(id); // Retourne JSON brut pour l'instant
    }


    @GetMapping
    public List<Card> getAllCards() {
        // Exemple statique pour tester
        List<Card> cards = new ArrayList<>();
        cards.add(new Card("1", "Bulbasaur", "https://pokeapi.co/media/sprites/1.png"));
        return cards;
    }

    @GetMapping("/{id}/image")
    public String getCardImage(@PathVariable String id) {
        // Simuler une URL d'image
        return "https://pokeapi.co/media/sprites/" + id + ".png";
    }


}
