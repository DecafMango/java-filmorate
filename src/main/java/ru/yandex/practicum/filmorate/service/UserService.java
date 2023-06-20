package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void createUser(User user) {
        userStorage.createUser(user);
    }

    public void updateUser(User user) {
        userStorage.updateUser(user);
    }

    public User getUser(long id) {
        if (!userStorage.getUsers().containsKey(id))
            throw new ObjectNotFoundException("Пользователь с id=" + id + " не найден");
        log.info("Sent user (id=" + id + ")");
        return userStorage.getUsers().get(id);
    }

    public Map<Long, User> getUsers() {
        return userStorage.getUsers();
    }

    public List<User> getFriends(long id) {
        Map<Long, User> users = userStorage.getUsers();
        if (!users.containsKey(id))
            throw new ObjectNotFoundException("Пользователь с id=" + id + " не найден");
        User user = users.get(id);
        log.info("Sent all user's (id=" + id + ") friends");
        return user.getFriends()
                .stream()
                .map(users::get)
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(long id, long otherId) {
        Map<String, User> users = getTwoUsers(id, otherId);
        Set<Long> commonFriends = new HashSet<>(users.get("user1").getFriends());
        commonFriends.retainAll(users.get("user2").getFriends());
        log.info("Sent users' (id=" + id + "/" + otherId + ") common friends");
        return commonFriends
                .stream()
                .map(userStorage.getUsers()::get)
                .collect(Collectors.toList());
    }

    public void addFriend(long id, long friendId) {
        Map<String, User> users = getTwoUsers(id, friendId);
        users.get("user1").getFriends().add(friendId);
        users.get("user2").getFriends().add(id);
        log.info("Users (id=" + id + "/" + friendId + ") made friend relationships");
    }

    public void deleteFriend(long id, long friendId) {
        Map<String, User> users = getTwoUsers(id, friendId);
        users.get("user1").getFriends().remove(friendId);
        users.get("user2").getFriends().remove(id);
        log.info("Users (id=" + id + "/" + friendId + ") broke friend relationships");
    }

    private Map<String, User> getTwoUsers(long id1, long id2) {
        Map<Long, User> users = userStorage.getUsers();
        if (!users.containsKey(id1))
            throw new ObjectNotFoundException("Пользователь с id=" + id1 + " не найден");
        if (!users.containsKey(id2))
            throw new ObjectNotFoundException("Пользователь с id=" + id2 + " не найден");
        User user1 = users.get(id1);
        User user2 = users.get(id2);

        Map<String, User> result = new HashMap<>();
        result.put("user1", user1);
        result.put("user2", user2);

        return result;
    }
}
