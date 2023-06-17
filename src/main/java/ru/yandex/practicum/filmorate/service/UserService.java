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
        return user.getFriends().stream().map(users::get).collect(Collectors.toList());
    }

    public List<User> getCommonFriends(long id, long otherId) {
        Map<Long, User> users = userStorage.getUsers();
        if (!users.containsKey(id))
            throw new ObjectNotFoundException("Пользователь с id=" + id + " не найден");
        if (!users.containsKey(otherId))
            throw new ObjectNotFoundException("Пользователь с id=" + otherId + " не найден");
        Set<Long> commonFriends = new HashSet<>(users.get(id).getFriends());
        commonFriends.retainAll(users.get(otherId).getFriends());
        log.info("Sent users' (id=" + id + "/" + otherId + ") common friends");
        return commonFriends.stream().map(users::get).collect(Collectors.toList());
    }

    public void addFriend(long id, long friendId) {
        Map<Long, User> users = userStorage.getUsers();
        if (!users.containsKey(id))
            throw new ObjectNotFoundException("Пользователь с id=" + id + " не найден");
        if (!users.containsKey(friendId))
            throw new ObjectNotFoundException("Пользователь с id=" + friendId + " не найден");
        User user = users.get(id);
        User friend = users.get(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(id);
        log.info("Users (id=" + id + "/" + friendId + ") made friend relationships");
    }

    public void deleteFriend(long id, long friendId) {
        Map<Long, User> users = userStorage.getUsers();
        if (!users.containsKey(id))
            throw new ObjectNotFoundException("Пользователь с id=" + id + " не найден");
        if (!users.containsKey(friendId))
            throw new ObjectNotFoundException("Пользователь с id=" + friendId + " не найден");
        User user = users.get(id);
        User friend = users.get(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(id);
        log.info("Users (id=" + id + "/" + friendId + ") broke friend relationships");
    }

}
