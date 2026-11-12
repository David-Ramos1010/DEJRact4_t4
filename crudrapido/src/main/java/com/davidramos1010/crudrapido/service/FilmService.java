package com.davidramos1010.crudrapido.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.davidramos1010.crudrapido.dto.FilmRequest;
import com.davidramos1010.crudrapido.dto.FilmResponse;
import com.davidramos1010.crudrapido.dto.PageResponse;
import com.davidramos1010.crudrapido.entity.Film;
import com.davidramos1010.crudrapido.entity.Language;
import com.davidramos1010.crudrapido.repository.FilmRepository;
import com.davidramos1010.crudrapido.repository.LanguageRepository;

@Service
public class FilmService {

    private final FilmRepository filmRepo;
    private final LanguageRepository languageRepo;

    public FilmService(FilmRepository filmRepo, LanguageRepository languageRepo) {
        this.filmRepo = filmRepo;
        this.languageRepo = languageRepo;
    }

    @Transactional(readOnly = true)
    public PageResponse<FilmResponse> list(int page, int size) {
        Page<Film> p = filmRepo.findAll(
                PageRequest.of(Math.max(page, 0), Math.min(Math.max(size, 1), 100), Sort.by("id")));
        return new PageResponse<>(p.getContent().stream().map(FilmResponse::from).toList(),
                p.getNumber(), p.getSize(), p.getTotalElements(), p.getTotalPages(), p.isLast());
    }

    @Transactional(readOnly = true)
    public FilmResponse get(Integer id) {
        return FilmResponse.from(find(id));
    }

    @Transactional
    public FilmResponse create(FilmRequest req) {
        return FilmResponse.from(filmRepo.save(apply(new Film(), req)));
    }

    @Transactional
    public FilmResponse update(Integer id, FilmRequest req) {
        return FilmResponse.from(filmRepo.save(apply(find(id), req)));
    }

    @Transactional
    public void delete(Integer id) {
        if (!filmRepo.existsById(id)) {
            throw notFound(id);
        }
        filmRepo.deleteById(id);
        filmRepo.flush(); // fuerza el error aquí si la película tiene datos relacionados (inventario, etc.)
    }

    private Film find(Integer id) {
        return filmRepo.findById(id).orElseThrow(() -> notFound(id));
    }

    private ResponseStatusException notFound(Integer id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Película no encontrada: " + id);
    }

    private Film apply(Film f, FilmRequest req) {
        Language lang = languageRepo.findById(req.languageId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El idioma no existe"));
        f.setTitle(req.title().trim());
        f.setDescription(req.description());
        f.setRentalDuration(req.rentalDuration() != null ? req.rentalDuration() : 3);
        f.setRentalRate(req.rentalRate() != null ? req.rentalRate() : new BigDecimal("4.99"));
        f.setLength(req.length());
        f.setRating(req.rating());
        f.setLanguage(lang);
        return f;
    }
}