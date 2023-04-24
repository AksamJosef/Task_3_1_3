package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Repository
public class UserDaoImpl implements UserDao{

    private final EntityManager em;

    @Autowired
    public UserDaoImpl(EntityManager em) {
        this.em = em;
    }


    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.roles  WHERE u.username = :username", User.class);
        query.setParameter("username", username);

        return query.getResultList().stream()
                .findAny()
                .orElse(null);
    }
}
