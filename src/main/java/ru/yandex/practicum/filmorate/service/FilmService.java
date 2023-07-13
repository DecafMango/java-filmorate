package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {

    @Autowired
    @Qualifier("dbStorage")
    private FilmStorage filmStorage;
    @Autowired
    @Qualifier("dbStorage")
    private UserStorage userStorage;


    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public Film getFilm(int id) {
        if (!filmStorage.getFilms().containsKey(id))
            throw new ObjectNotFoundException("Фильм с id=" + id + " не найден");
        log.info("Sent film (id=" + id + ")");
        return filmStorage.getFilms().get(id);
    }

    public Map<Integer, Film> getFilms() {
        return filmStorage.getFilms();
    }

    public List<Film> getPopularFilms(Optional<Integer> count) {
        Collection<Film> films = filmStorage.getFilms().values();
        log.info("Sent " + (count.isPresent() ? count.get() : 10) + " popular films");
        return films.stream()
                .sorted((film1, film2) -> film2.getFollowers().size() - film1.getFollowers().size())
                .limit(count.isPresent() ? count.get() : 10)
                .collect(Collectors.toList());
    }

    public Film like(int filmId, int userId) {
        Map<String, Object> filmAndUser = getFilmUser(filmId, userId);
        Film film = (Film) filmAndUser.get("film");
        User user = (User) filmAndUser.get("user");
        if (!film.getFollowers().contains(user))   //Проверка на наличие лайка
            film.getFollowers().add(user);
        filmStorage.updateFilm(film);
        log.info("User (id=" + userId + ") liked film (id=" + filmId + ")");
        return film;
    }

    public Film dislike(int filmId, int userId) {
        Map<String, Object> filmAndUser = getFilmUser(filmId, userId);
        Film film = (Film) filmAndUser.get("film");
        User user = (User) filmAndUser.get("user");

        if (film.getFollowers().contains(user)) //Проверка на наличие лайка
            film.getFollowers().remove(user);
        log.info("User (id=" + userId + ") disliked film (id=" + filmId + ")");
        return film;
    }

    public Genre getGenre(int id) {
        Map<Integer, Genre> genres = filmStorage.getGenres();
        if (!genres.containsKey(id))
            throw new ObjectNotFoundException("Жанр с id=" + id + " не найден");
        return genres.get(id);
    }

    public Map<Integer, Genre> getGenres() {
        return filmStorage.getGenres();
    }

    public Mpa getMpa(int id) {
        Map<Integer, Mpa> mpa = filmStorage.getMpa();
        if (!mpa.containsKey(id))
            throw new ObjectNotFoundException("Рейтинг с id=" + id + " не найден");
        return mpa.get(id);
    }

    public Map<Integer, Mpa> getAllMpa() {
        return filmStorage.getMpa();
    }

    private Map<String, Object> getFilmUser(int filmId, int userId) {
        Map<Integer, Film> films = filmStorage.getFilms();
        Map<Integer, User> users = userStorage.getUsers();
        if (!films.containsKey(filmId))
            throw new ObjectNotFoundException("Фильм с id=" + filmId + " не найден");
        if (!users.containsKey(userId))
            throw new ObjectNotFoundException("Пользователь с id=" + userId + " не найден");

        Map<String, Object> result = new HashMap<>();
        result.put("film", films.get(filmId));
        result.put("user", users.get(userId));
        return result;
    }

}
