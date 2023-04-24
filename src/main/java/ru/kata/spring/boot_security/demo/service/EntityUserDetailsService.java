package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.security.EntityUserDetails;


@Service
public class EntityUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    private User currentUser;


    @Autowired
    public EntityUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        currentUser = user;

        if (user == null) throw new UsernameNotFoundException("User not found");

        return new EntityUserDetails(user);
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
