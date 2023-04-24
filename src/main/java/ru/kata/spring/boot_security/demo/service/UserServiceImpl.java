package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RolesRepository;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;
import ru.kata.spring.boot_security.demo.security.EntityUserDetails;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;

    private final UserDao userDao;
    @Autowired
    public UserServiceImpl(UsersRepository usersRepository, RolesRepository rolesRepository, UserDao userDao) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.userDao = userDao;
    }


    @Transactional
    @Override
    public void addAsUser(User user) {
        user.addRole(rolesRepository.getById(1));
        usersRepository.save(user);
    }

    @Transactional
    @Override
    public List<User> getUserList() {
        return usersRepository.findAll();
    }


    @Transactional
    @Override
    public void delete(int id) {
        usersRepository.deleteById(id);
    }


    @Transactional
    @Override
    public void update(int id, User user) {

        User userToUpdate = getUserById(id);

        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setName(user.getName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setEmail(user.getEmail());


        usersRepository.save(userToUpdate);
    }


    @Transactional
    @Override
    public User getUserById(int id) {
        return usersRepository.findById(id).orElse(null);
    }


}
