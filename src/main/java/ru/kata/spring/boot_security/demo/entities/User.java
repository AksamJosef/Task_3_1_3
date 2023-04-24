package ru.kata.spring.boot_security.demo.entities;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    @NotEmpty(message = "Username can't be empty")
    @Size(min = 2, max = 30, message = "Size of the field could be between 2 and 30 characters")
    private String username;

    @Column(name = "password")
    @NotEmpty(message = "Password can't be empty")
    private String password;

    @Column(name = "name")
    @NotEmpty(message = "Field can't be empty")
    @Size(min = 2, max = 30, message = "Size of the field could be between 2 and 30 characters")
    private String name;

    @NotEmpty(message = "Field can't be empty")
    @Size(min = 2, max = 30, message = "Size of the field could be between 2 and 30 characters")
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message = "Please, enter email!")
    @Email(message = "This is not email!")
    @Column(name = "email")
    private String email;


    //todo переделать на OneToMany


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                            CascadeType.REFRESH, CascadeType.DETACH}
                            , fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public void addRole(Role role) {
        if (roles == null) roles = new HashSet<>();
        roles.add(role);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Set<Role> getRoles() {
        return roles;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }
}

