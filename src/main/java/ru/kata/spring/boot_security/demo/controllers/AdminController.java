package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.validators.UserValidator;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final UserValidator userValidator;
    @Autowired
    public AdminController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/")
    public String getAdminPage(Model model) {
        model.addAttribute("users", userService.getUserList());
        return "admin_pages/admin";
    }

    @GetMapping("/new")
    public String addNewUser(@ModelAttribute("user") User user) {
        return "admin_pages/new";
    }


    @PostMapping("/")
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) return "admin_pages/new";
        userService.addAsUser(user);
        return "redirect:/admin/";
    }


    @GetMapping("/edit/{id}")
    public String editUser(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin_pages/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute ("user") @Valid User user,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {

        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) return "admin_pages/edit";

        userService.update(id, user);
        return "redirect:/admin/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        userService.delete(id);
        return "redirect:/admin/";
    }



}
