package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserValidator implements Validator<User> {

    @Override
    public boolean validate(User user) {
        if (user.getLogin().contains(" "))
            return false;
        if (user.getBirthday().isAfter(LocalDate.now()))
            return false;

        //Todo Спросить у ревьюера, куда лучше присваивание имени поставить (потому что этот метод только для валидации)
        if (user.getName() == null || user.getName().isBlank())
            user.setName(user.getLogin());

        return true;
    }

}
