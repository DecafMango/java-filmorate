package ru.yandex.practicum.filmorate.model.film;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class Mpa {
    private final int id;
    @Nullable
    private final String name;
}
