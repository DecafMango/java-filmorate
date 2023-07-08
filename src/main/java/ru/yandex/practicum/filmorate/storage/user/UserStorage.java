package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.user.User;

import java.util.Map;

public interface UserStorage {
    User createUser(User user);

    void deleteUser(int id);

    User updateUser(User user);

    Map<Integer, User> getUsers();
}
