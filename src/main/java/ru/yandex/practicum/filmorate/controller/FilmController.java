package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.FilmValidator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private Map<Integer, Film> films = new HashMap<>();
    private final FilmValidator validator = new FilmValidator();
    private int idCounter = 1;

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        if (!validator.validate(film)) {
            log.warn("Invalid " + film);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        film.setId(idCounter);
        films.put(idCounter, film);
        idCounter++;
        log.info("Created " + film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (!validator.validate(film)) {
            log.warn("Invalid " + film);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (film.getId() <= 0 || !films.containsKey(film.getId())) {
            log.warn("Invalid id " + film);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        films.put(film.getId(), film);
        log.info("Updated " + film);
        return film;
    }

    @GetMapping
    public Collection<Film> getFilms() {
        log.info("Sent film collection");
        return films.values();
    }
}
