package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class Film {
    private int id;
    @NotBlank
    private final String name;
    @NotNull
    private final String description;
    @NotNull
    private final LocalDate releaseDate;
    private final int duration; //Я указывал класс Duration, но тесты в Постмане требуют целое значение :(
}
