package ru.kata.spring.boot_security.demo.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.entities.User;


@Component
public class UserValidator implements Validator {

    private final UserDao userDao;


    @Autowired
    public UserValidator(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        User userInDB = userDao.findByUsername(user.getUsername());

        if((userInDB != null)
                && (user.getId() != userInDB.getId())) {

            errors.rejectValue("username", "",
                    "There are already exists user with this username!");
        }
    }
}
