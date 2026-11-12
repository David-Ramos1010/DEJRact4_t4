package com.davidramos1010.crudrapido.dto;

import java.math.BigDecimal;
import com.davidramos1010.crudrapido.entity.Film;

public record FilmResponse(Integer id, String title, String description, Integer rentalDuration,
                           BigDecimal rentalRate, Integer length, String rating,
                           Integer languageId, String languageName) {

    public static FilmResponse from(Film f) {
        return new FilmResponse(f.getId(), f.getTitle(), f.getDescription(), f.getRentalDuration(),
                f.getRentalRate(), f.getLength(), f.getRating(),
                f.getLanguage().getId(), f.getLanguage().getName());
    }
}