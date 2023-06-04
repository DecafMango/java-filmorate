package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.FilmValidator;

import java.time.LocalDate;

public class FilmValidatorTest {

    private final FilmValidator validator = new FilmValidator();

    @Test
    public void shouldBeValid() {
        Film film = new Film("Test Film", "It's a test film", LocalDate.now(), 100);
        Assertions.assertTrue(validator.validate(film));
    }

    @Test
    public void shouldNotBeValidForInvalidDescription() {
        Film film = new Film("Test Film", "1".repeat(201), LocalDate.now(), 100);
        Assertions.assertFalse(validator.validate(film));
    }

    @Test
    public void shouldNotBeValidForInvalidReleaseDate() {
        Film film = new Film("Test Film", "It's a test film",
                LocalDate.of(1895, 12, 27), 100);
        Assertions.assertFalse(validator.validate(film));
    }

    @Test
    public void shouldNotBeValidForInvalidDuration() {
        Film film = new Film("Test Film", "It's a test film",
                LocalDate.now(), 0);
        Assertions.assertFalse(validator.validate(film));
    }

}
