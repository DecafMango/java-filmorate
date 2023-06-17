package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();
    private long idCounter = 0;

    @Override
    public void createFilm(Film film) {
        film.setId(++idCounter);
        films.put(idCounter, film);
        log.info("Created " + film);
    }

    @Override
    public void deleteFilm(long id) {
        if (!films.containsKey(id))
            throw new ObjectNotFoundException("Фильм с id=" + id + " не найден");
        films.remove(id);
        log.info("Deleted film (id=" + id + ")");
    }

    @Override
    public void updateFilm(Film film) {
        if (!films.containsKey(film.getId()))
            throw new ObjectNotFoundException("Фильм с id=" + film.getId() + " не найден");
        films.put(film.getId(), film);
        log.info("Updated " + film);
    }

    @Override
    public Map<Long, Film> getFilms() {
        log.info("Sent all films");
        return films;
    }
}
