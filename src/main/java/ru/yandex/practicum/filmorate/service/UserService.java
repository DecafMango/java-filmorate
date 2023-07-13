package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    @Autowired
    @Qualifier("dbStorage")
    private UserStorage userStorage;

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public User getUser(int id) {
        if (!userStorage.getUsers().containsKey(id))
            throw new ObjectNotFoundException("Пользователь с id=" + id + " не найден");
        log.info("Sent user (id=" + id + ")");
        return userStorage.getUsers().get(id);
    }

    public Map<Integer, User> getUsers() {
        return userStorage.getUsers();
    }

    public List<User> getFriends(int id) {
        Map<Integer, User> users = userStorage.getUsers();
        if (!users.containsKey(id))
            throw new ObjectNotFoundException("Пользователь с id=" + id + " не найден");
        User user = users.get(id);
        log.info("Sent all user's (id=" + id + ") friends");
        return user.getFriends()
                .stream()
                .sorted(Comparator.comparingInt(User::getId))
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(int id, int otherId) {
        Map<String, User> users = getTwoUsers(id, otherId);
        Set<User> commonFriends = new HashSet<>(users.get("user1").getFriends());
        commonFriends.retainAll(users.get("user2").getFriends());
        log.info("Sent users' (id=" + id + "/" + otherId + ") common friends");
        return new ArrayList<>(commonFriends);
    }

    public void addFriend(int id, int friendId) {
        Map<String, User> users = getTwoUsers(id, friendId);
        User user1 = users.get("user1");
        User user2 = users.get("user2");
        user1.getFriends().add(user2);
        userStorage.updateUser(user1);
        log.info("User (id=" + id  + ") made friend-request to user (id=" + friendId + ")");
    }

    public void deleteFriend(int id, int friendId) {
        Map<String, User> users = getTwoUsers(id, friendId);
        User user1 = users.get("user1");
        User user2 = users.get("user2");
        user1.getFriends().remove(user2);
        userStorage.updateUser(user1);
        log.info("User (id=" + id + ") canceled friend-request to user (id=" + friendId + ")");
    }

    private Map<String, User> getTwoUsers(int id_1, int id_2) {
        Map<Integer, User> users = userStorage.getUsers();
        if (!users.containsKey(id_1))
            throw new ObjectNotFoundException("Пользователь с id=" + id_1 + " не найден");
        if (!users.containsKey(id_2))
            throw new ObjectNotFoundException("Пользователь с id=" + id_2 + " не найден");
        User user1 = users.get(id_1);
        User user2 = users.get(id_2);

        Map<String, User> result = new HashMap<>();
        result.put("user1", user1);
        result.put("user2", user2);

        return result;
    }
}
