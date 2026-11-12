package com.davidramos1010.crudrapido.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.davidramos1010.crudrapido.dto.FilmRequest;
import com.davidramos1010.crudrapido.dto.FilmResponse;
import com.davidramos1010.crudrapido.dto.PageResponse;
import com.davidramos1010.crudrapido.service.FilmService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/films")
public class FilmController {

    private final FilmService service;

    public FilmController(FilmService service) {
        this.service = service;
    }

    // GET /api/films?page=0&size=10
    @GetMapping
    public ResponseEntity<PageResponse<FilmResponse>> list(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.list(page, size));
    }

    // GET /api/films/{id}
    @GetMapping("/{id}")
    public ResponseEntity<FilmResponse> get(@PathVariable Integer id) {
        return ResponseEntity.ok(service.get(id));
    }

    // POST /api/films
    @PostMapping
    public ResponseEntity<FilmResponse> create(@Valid @RequestBody FilmRequest req) {
        FilmResponse created = service.create(req);
        return ResponseEntity.created(URI.create("/api/films/" + created.id())).body(created);
    }

    // PUT /api/films/{id}
    @PutMapping("/{id}")
    public ResponseEntity<FilmResponse> update(@PathVariable Integer id, @Valid @RequestBody FilmRequest req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    // DELETE /api/films/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}