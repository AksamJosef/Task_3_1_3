package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.security.EntityUserDetails;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;


@Deprecated
public class OLDUserController {

    private final UserService userService;

    @Autowired
    public OLDUserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/index")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/user")
    public String getUserPage() {
        return "user";
    }

    @GetMapping("/")
    public String getUsers(Model model) {
        List<User> userList = userService.getUserList();
        model.addAttribute("userList", userList);
        return "pages/users";
    }

    @GetMapping("/new")
    public String addNewUser(@ModelAttribute("user") User user) {
        return "pages/new";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getUserById(id));
        return "pages/edit";
    }



    @PostMapping("/")
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "pages/new";
//        userService.add(user);
        return "redirect:/";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute ("user") @Valid User user,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) return "pages/edit";
        userService.update(id, user);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        userService.delete(id);
        return "redirect:/";
    }


    @GetMapping("/showAuth")
    public String showAuth() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        EntityUserDetails userDetails = (EntityUserDetails) authentication.getPrincipal();

        System.out.println(userDetails.getUser());

        return "redirect:/";
    }

}
