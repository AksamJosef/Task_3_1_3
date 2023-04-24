package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;
import ru.kata.spring.boot_security.demo.security.EntityUserDetails;

import java.util.Optional;


@Service
public class EntityUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    private User currentUser;


    @Autowired
    public EntityUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userDao.findByUsername(username);

        Optional<User> user = usersRepository.findByUsername(username);

        if (user.isEmpty()) throw new UsernameNotFoundException("User not found");

        currentUser = user.get();

        return new EntityUserDetails(currentUser);
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
