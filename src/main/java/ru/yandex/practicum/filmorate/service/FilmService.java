package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage filmStorage, InMemoryUserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void createFilm(Film film) {
        filmStorage.createFilm(film);
    }

    public void updateFilm(Film film) {
        filmStorage.updateFilm(film);
    }

    public Film getFilm(long id) {
        if (!filmStorage.getFilms().containsKey(id))
            throw new ObjectNotFoundException("Фильм с id=" + id + " не найден");
        log.info("Sent film (id=" + id + ")");
        return filmStorage.getFilms().get(id);
    }

    public Map<Long, Film> getFilms() {
        return filmStorage.getFilms();
    }

    public List<Film> getPopularFilms(Optional<Long> count) {
        Collection<Film> films = filmStorage.getFilms().values();
        log.info("Sent " + (count.isPresent() ? count.get() : 10) + " popular films");
        return films.stream()
                .sorted((film1, film2) -> film2.getFans().size() - film1.getFans().size())
                .limit(count.isPresent() ? count.get() : 10)
                .collect(Collectors.toList());
    }

    public Film like(long filmId, long userId) {
        if (!filmStorage.getFilms().containsKey(filmId))
            throw new ObjectNotFoundException("Фильм с id=" + filmId + " не найден");
        if (!userStorage.getUsers().containsKey(userId))
            throw new ObjectNotFoundException("Пользователь с id=" + userId + " не найден");
        Film film = filmStorage.getFilms().get(filmId);
        if (!film.getFans().contains(userId))   //Проверка на наличие лайка
            film.getFans().add(userId);
        log.info("User (id=" + userId + ") liked film (id=" + filmId + ")");
        return film;
    }

    public Film dislike(long filmId, long userId) {
        if (!filmStorage.getFilms().containsKey(filmId))
            throw new ObjectNotFoundException("Фильм с id=" + filmId + " не найден");
        if (!userStorage.getUsers().containsKey(userId))
            throw new ObjectNotFoundException("Пользователь с id=" + userId + " не найден");
        Film film = filmStorage.getFilms().get(filmId);
        if (film.getFans().contains(userId))
            film.getFans().remove(userId);
        log.info("User (id=" + userId + ") disliked film (id=" + filmId + ")");
        return film;
    }

}
