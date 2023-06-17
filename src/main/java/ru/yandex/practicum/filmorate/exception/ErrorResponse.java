package ru.yandex.practicum.filmorate.exception;

import lombok.Data;

@Data
public class ErrorResponse {
    private final String error; //Название ошибки
    private final String description; //Описание ошибки

}
