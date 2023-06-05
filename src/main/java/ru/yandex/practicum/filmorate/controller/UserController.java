package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private Map<Integer, User> users = new HashMap<>();
    private final UserValidator validator = new UserValidator();
    private int idCounter = 1;

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        if (!validator.validate(user)) {
            log.warn("Invalid " + user);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        user.setId(idCounter);
        users.put(idCounter, user);
        idCounter++;
        log.info("Created " + user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        if (!validator.validate(user)) {
            log.warn("Invalid object " + user);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (!users.containsKey(user.getId())) {
            log.warn("Invalid id " + user);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        users.put(user.getId(), user);
        log.info("Updated " + user);
        return user;
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("Sent users collection");
        return new ArrayList<>(users.values());
    }
}
