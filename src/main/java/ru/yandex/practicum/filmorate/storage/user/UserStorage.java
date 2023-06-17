package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

public interface UserStorage {
    void createUser(User user);

    void deleteUser(long id);

    void updateUser(User user);

    Map<Long, User> getUsers();
}
