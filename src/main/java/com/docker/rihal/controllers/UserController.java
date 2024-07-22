package com.docker.rihal.controllers;



import com.docker.rihal.models.User;
import com.docker.rihal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "index";
    }

    @GetMapping("/users/new")
    public String showNewUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "new_user";
    }

    @PostMapping("/users")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditUserForm(@PathVariable(value = "id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "edit_user";
    }

    @PostMapping("/users/{id}")
    public String updateUser(@PathVariable(value = "id") Long id, @ModelAttribute("user") User user) {
        User existingUser = userService.getUserById(id);
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        userService.saveUser(existingUser);
        return "redirect:/";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/";
    }

    // REST APIs
    @GetMapping("/api/users")
    @ResponseBody
    public List<User> getAllUsersApi() {
        return userService.getAllUsers();
    }

    @GetMapping("/api/users/{id}")
    @ResponseBody
    public User getUserByIdApi(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/api/users")
    @ResponseBody
    public User saveUserApi(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("/api/users/{id}")
    @ResponseBody
    public void deleteUserApi(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
