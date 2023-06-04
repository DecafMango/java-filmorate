package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class Film {
    private int id;
    private @NonNull @NotBlank final String name;
    private @NonNull final String description;
    private @NonNull final LocalDate releaseDate;
    private final int duration; //Я указывал класс Duration, но тесты в Постмане требуют целое значение :(
}
