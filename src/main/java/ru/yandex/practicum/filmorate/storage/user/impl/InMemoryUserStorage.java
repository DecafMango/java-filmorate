package ru.yandex.practicum.filmorate.storage.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.HashMap;
import java.util.Map;

@Component
@Qualifier("inMemoryStorage")
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private int idCounter = 0;

    @Override
    public User createUser(User user) {
        user.setId(++idCounter);
        users.put(idCounter, user);
        log.info("Created " + user);
        return user;
    }

    @Override
    public void deleteUser(int id) {
        if (!users.containsKey(id))
            throw new ObjectNotFoundException("Пользователь с id=" + id + " не найден");
        users.remove(id);
        log.info("Removed user (id=" + id + ")");
    }

    @Override
    public User updateUser(User user) {
        if (!users.containsKey(user.getId()))
            throw new ObjectNotFoundException("Пользователь с id=" + user.getId() + " не найден");
        users.put(user.getId(), user);
        log.info("Updated " + user);
        return user;
    }

    @Override
    public Map<Integer, User> getUsers() {
        log.info("Got all users");
        return users;
    }
}
