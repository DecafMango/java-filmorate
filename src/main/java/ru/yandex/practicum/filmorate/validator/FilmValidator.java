package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class FilmValidator implements Validator<Film> {

    //Введем дату-ограничение, раньше которой нельзя ввести фильм
    private static final LocalDate CINEMA_BIRTHDAY = LocalDate.of(1895, 12, 28);


    @Override
    public boolean validate(Film film) {
        if (film.getDescription().length() > 200)
            return false;
        if (film.getReleaseDate().isBefore(CINEMA_BIRTHDAY))
            return false;
        if (film.getDuration() <= 0)
            return false;
        return true;
    }
}
