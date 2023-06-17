package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Map;

public interface FilmStorage {
    void createFilm(Film film);

    void deleteFilm(long id);

    void updateFilm(Film film);

    Map<Long, Film> getFilms();
}
