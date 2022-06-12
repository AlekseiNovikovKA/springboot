package com.example.springboot.controller;


import com.example.springboot.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    public String index() {
        return "index";
    }


    @GetMapping("/admin/")
    public String adminIndex(Model model) {
        model.addAttribute("user", userService.getAllUsers());
        return "/admin/index";
    }

    @GetMapping("/admin/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "admin/new";
    }

    @PostMapping("/admin/new")
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/admin/new";
        }
        userService.addUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/admin/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "/admin/edit";
    }

    @PatchMapping("/admin/edit/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "/admin/edit";
        }
        userService.updateUser(user, id);
        return "redirect:/admin/";
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.removeUser(id);
        return "redirect:/admin/";
    }

    @GetMapping("/user/")
    public String userPage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        model.addAttribute("user", userDetails.getUser());
        return "/user/index";
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
