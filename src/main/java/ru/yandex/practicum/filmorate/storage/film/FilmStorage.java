package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.Mpa;

import java.util.Map;

public interface FilmStorage {
    Film createFilm(Film film);

    void deleteFilm(int id);

    Film updateFilm(Film film);

    Map<Integer, Genre> getGenres();

    Map<Integer, Mpa> getMpa();

    Map<Integer, Film> getFilms();
}
