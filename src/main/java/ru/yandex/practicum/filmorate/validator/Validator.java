package ru.yandex.practicum.filmorate.validator;

public interface Validator<T> {
    void validate(T object); //При возникновении исключения метод возвращает его описание, иначе - null
}
