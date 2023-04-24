package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface UserService {

    void addAsUser (User user);

    List<User> getUserList();

    void delete (int id);

    void update (int id, User user);

    User getUserById(int id);
}
