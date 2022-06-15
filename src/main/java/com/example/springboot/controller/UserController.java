package com.example.springboot.controller;


import com.example.springboot.Util.UserValidator;
import com.example.springboot.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final RoleService roleService;
    private final UserValidator userValidator;

    @Autowired
    public UserController(UserService userService, RoleService roleService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
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
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("rolelist", roleService.getAllRole());
        return "admin/new";
    }

    @PostMapping("/admin/new")
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult, Model model) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("rolelist", roleService.getAllRole());
            return "admin/new";
        }
        userService.addUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/admin/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("rolelist", roleService.getAllRole());
        return "/admin/edit";
    }

    @PatchMapping("/admin/edit/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @PathVariable("id") Long id, Model model) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("rolelist", roleService.getAllRole());
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
        User user = (User) authentication.getPrincipal();

        model.addAttribute("user", user);
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
