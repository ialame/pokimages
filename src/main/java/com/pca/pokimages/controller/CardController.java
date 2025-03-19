package com.pca.pokimages.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String POKEMON_TCG_API = "https://api.pokemontcg.io/v2/cards";

    @GetMapping
    public List<Card> getAllCards() {
        PokemonTcgResponse response = restTemplate.getForObject(POKEMON_TCG_API + "?q=name:bulbasaur", PokemonTcgResponse.class);
        if (response == null || response.getData() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune carte trouvée");
        }
        return response.getData();
    }

    @GetMapping("/{id}")
    public Card getCardById(@PathVariable String id) {
        PokemonTcgCardResponse response = restTemplate.getForObject(POKEMON_TCG_API + "/" + id, PokemonTcgCardResponse.class);
        if (response == null || response.getData() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Carte non trouvée : " + id);
        }
        return response.getData();
    }
}

class PokemonTcgResponse {
    private List<Card> data;

    public List<Card> getData() {
        return data;
    }

    public void setData(List<Card> data) {
        this.data = data;
    }
}

class PokemonTcgCardResponse {
    private Card data;

    public Card getData() {
        return data;
    }

    public void setData(Card data) {
        this.data = data;
    }
}

class Card {
    private String id;
    private String name;
    private Images images;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }
}

class Images {
    private String small;
    private String large;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }
}