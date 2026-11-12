package com.davidramos1010.crudrapido.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


public record FilmRequest(
        @NotBlank(message = "El título es obligatorio")
        @Size(max = 128, message = "El título no puede pasar de 128 caracteres") String title,
        String description,
        @Min(value = 1, message = "La duración de renta debe ser al menos 1") Integer rentalDuration,
        @DecimalMin(value = "0.0", message = "La tarifa no puede ser negativa") BigDecimal rentalRate,
        @Positive(message = "La duración debe ser positiva") Integer length,
        @Pattern(regexp = "G|PG|PG-13|R|NC-17", message = "Rating debe ser G, PG, PG-13, R o NC-17") String rating,
        @NotNull(message = "El idioma (languageId) es obligatorio") Integer languageId) {
}