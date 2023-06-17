package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.FilmValidator;

import java.time.LocalDate;

public class FilmValidatorTest {

    private final FilmValidator filmValidator = new FilmValidator();

    @Test
    public void shouldBeValid() {
        Film film = new Film("Test Film", "It's a test film", LocalDate.now(), 100);
        try {
            filmValidator.validate(film);
            Assertions.assertTrue(true);
        } catch (ValidationException e) {
            Assertions.fail();
        }
    }

    @Test
    public void shouldNotBeValidForInvalidDescription() {
        Film film = new Film("Test Film", "1".repeat(201), LocalDate.now(), 100);
        try {
            filmValidator.validate(film);
            Assertions.fail();
        } catch (ValidationException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void shouldNotBeValidForInvalidReleaseDate() {
        Film film = new Film("Test Film", "It's a test film",
                LocalDate.of(1895, 12, 27), 100);
        try {
            filmValidator.validate(film);
            Assertions.fail();
        } catch (ValidationException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void shouldNotBeValidForInvalidDuration() {
        Film film = new Film("Test Film", "It's a test film",
                LocalDate.now(), 0);
        try {
            filmValidator.validate(film);
            Assertions.fail();
        } catch (ValidationException e) {
            Assertions.assertTrue(true);
        }
    }

}
