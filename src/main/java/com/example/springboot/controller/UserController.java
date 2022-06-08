package com.example.springboot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.springboot.model.User;
import com.example.springboot.service.UserService;

import javax.validation.Valid;



@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("user", userService.getAllUsers());
        return "index";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/new";
        }
        userService.addUser(user);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }

    @PatchMapping("/edit/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "/edit";
        }
        userService.updateUser(user, id);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.removeUser(id);
        return "redirect:/";
    }


    @GetMapping("css/styletable.css")
    public String styleTable() {
        return "css/styletable.css";
    }

    @GetMapping("css/styleform.css")
    public String styleForm() {
        return "css/styleform.css";
    }

}
