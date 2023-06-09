package ru.yandex.practicum.filmorate.storage.film.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.HashMap;
import java.util.Map;

@Component
@Qualifier("inMemoryStorage")
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();
    private int idCounter = 0;

    @Override
    public Film createFilm(Film film) {
        film.setId(++idCounter);
        films.put(idCounter, film);
        log.info("Created " + film);
        return film;
    }

    @Override
    public void deleteFilm(int id) {
        if (!films.containsKey(id))
            throw new ObjectNotFoundException("Фильм с id=" + id + " не найден");
        films.remove(id);
        log.info("Deleted film (id=" + id + ")");
    }

    @Override
    public Film updateFilm(Film film) {
        if (!films.containsKey(film.getId()))
            throw new ObjectNotFoundException("Фильм с id=" + film.getId() + " не найден");
        films.put(film.getId(), film);
        log.info("Updated " + film);
        return film;
    }

    @Override
    public Map<Integer, Genre> getGenres() {
        return null;  //Если потребуется - добавлю реализацию этого метода в таком виде хранения
    }

    @Override
    public Map<Integer, Mpa> getMpa() {
        return null;  //Если потребуется - добавлю реализацию этого метода в таком виде хранения
    }

    @Override
    public Map<Integer, Film> getFilms() {
        log.info("Sent all films");
        return films;
    }
}
