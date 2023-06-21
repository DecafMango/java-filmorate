package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.time.LocalDate;

public class UserValidatorTest {

    private final UserValidator userValidator = new UserValidator();

    @Test
    public void shouldBeValid() {
        User user = new User("DecafMangoITMO@yandex.ru", "DecafMango",
                LocalDate.of(2004, 3, 9));
        try {
            userValidator.validate(user);
            Assertions.assertTrue(true);
        } catch (ValidationException e) {
            Assertions.fail();
        }
    }

    @Test
    public void shouldNotBeValidForWhitespacesInLogin() {
        User user = new User("DecafMangoITMO@yandex.ru", "Decaf Mango",
                LocalDate.of(2004, 3, 9));
        try {
            userValidator.validate(user);
            Assertions.fail();
        } catch (ValidationException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void shouldNotBeValidForFutureBirthday() {
        User user = new User("DecafMangoITMO@yandex.ru", "DecafMango",
                LocalDate.now().plusDays(1));
        try {
            userValidator.validate(user);
            Assertions.fail();
        } catch (ValidationException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void shouldGiveName() {
        User user = new User("DecafMangoITMO@yandex.ru", "DecafMango",
                LocalDate.of(2004, 3, 9));
        String login = user.getLogin();
        String expectedLogin = "DecafMango";
        Assertions.assertEquals(expectedLogin, login);
    }
}
