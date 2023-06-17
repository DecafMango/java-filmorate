package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private long idCounter = 0;

    @Override
    public void createUser(User user) {
        user.setId(++idCounter);
        users.put(idCounter, user);
        log.info("Created " + user);
    }

    @Override
    public void deleteUser(long id) {
        if (!users.containsKey(id))
            throw new ObjectNotFoundException("Пользователь с id=" + id + " не найден");
        users.remove(id);
        log.info("Removed user (id=" + id + ")");
    }

    @Override
    public void updateUser(User user) {
        if (!users.containsKey(user.getId()))
            throw new ObjectNotFoundException("Пользователь с id=" + user.getId() + " не найден");
        users.put(user.getId(), user);
        log.info("Updated " + user);
    }

    @Override
    public Map<Long, User> getUsers() {
        log.info("Sent all users");
        return users;
    }
}
