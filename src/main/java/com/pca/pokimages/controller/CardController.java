package com.pca.pokimages.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String POKEMON_TCG_API = "https://api.pokemontcg.io/v2";

    // 1. Lister les séries (distinctes)
    @GetMapping("/series")
    public List<String> getSeries() {
        PokemonTcgSetsResponse response = restTemplate.getForObject(POKEMON_TCG_API + "/sets", PokemonTcgSetsResponse.class);
        if (response == null || response.getData() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune série trouvée");
        }
        return response.getData().stream()
                .map(Set::getSeries)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    // 2. Lister les extensions d’une série
    @GetMapping("/sets")
    public List<Set> getSetsBySeries(@RequestParam String series) {
        PokemonTcgSetsResponse response = restTemplate.getForObject(POKEMON_TCG_API + "/sets", PokemonTcgSetsResponse.class);
        if (response == null || response.getData() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune extension trouvée");
        }
        return response.getData().stream()
                .filter(set -> set.getSeries().equals(series))
                .sorted((a, b) -> a.getReleaseDate().compareTo(b.getReleaseDate()))
                .collect(Collectors.toList());
    }

    // 3. Lister les cartes d’un set
    @GetMapping("/cards")
    public List<Card> getCardsBySet(@RequestParam String setId) {
        PokemonTcgCardsResponse response = restTemplate.getForObject(
                POKEMON_TCG_API + "/cards?q=set.id:" + setId, PokemonTcgCardsResponse.class);
        if (response == null || response.getData() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune carte trouvée pour le set : " + setId);
        }
        return response.getData();
    }

    // Détails d’une carte (inchangé)
    @GetMapping("/cards/{id}")
    public Card getCardById(@PathVariable String id) {
        PokemonTcgCardResponse response = restTemplate.getForObject(POKEMON_TCG_API + "/cards/" + id, PokemonTcgCardResponse.class);
        if (response == null || response.getData() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Carte non trouvée : " + id);
        }
        return response.getData();
    }
}

// Classes de réponse
class PokemonTcgSetsResponse {
    private List<Set> data;
    public List<Set> getData() { return data; }
    public void setData(List<Set> data) { this.data = data; }
}

class PokemonTcgCardsResponse {
    private List<Card> data;
    public List<Card> getData() { return data; }
    public void setData(List<Card> data) { this.data = data; }
}

class PokemonTcgCardResponse {
    private Card data;
    public Card getData() { return data; }
    public void setData(Card data) { this.data = data; }
}

class Set {
    private String id;
    private String name;
    private String series;
    private String releaseDate;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSeries() { return series; }
    public void setSeries(String series) { this.series = series; }
    public String getReleaseDate() { return releaseDate; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }
}

class Card {
    private String id;
    private String name;
    private Images images;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Images getImages() { return images; }
    public void setImages(Images images) { this.images = images; }
}

class Images {
    private String small;
    private String large;

    public String getSmall() { return small; }
    public void setSmall(String small) { this.small = small; }
    public String getLarge() { return large; }
    public void setLarge(String large) { this.large = large; }
}