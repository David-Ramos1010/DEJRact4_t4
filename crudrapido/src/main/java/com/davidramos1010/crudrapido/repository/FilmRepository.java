package com.davidramos1010.crudrapido.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.davidramos1010.crudrapido.entity.Film;

public interface FilmRepository extends JpaRepository<Film, Integer> {
}