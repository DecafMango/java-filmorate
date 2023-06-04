package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class User {
    private int id;
    private @NonNull @Email final String email;
    private @NonNull @NotBlank final String login;
    private String name;
    private @NonNull final LocalDate birthday;
}
