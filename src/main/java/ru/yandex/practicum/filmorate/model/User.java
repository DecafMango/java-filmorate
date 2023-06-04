package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class User {
    private int id;
    private @NonNull @Email
    final String email;
    private @NonNull @NotBlank
    final String login;
    private String name;
    private @NonNull final LocalDate birthday;
}
