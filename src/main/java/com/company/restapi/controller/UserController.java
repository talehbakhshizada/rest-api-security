package com.company.restapi.controller;

import com.company.restapi.model.User;
import com.company.restapi.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable(name = "id") Long userId){
        return userService.getUserById(userId);
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User newUser){
        return userService.createUser(newUser);
    }


    @PutMapping("/users/{id}")
    public User updateUser(@RequestBody User newUser,@PathVariable Long id){
       return userService.updateUser(newUser,id);
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable(name = "id") Long userId){
        userService.deleteUser(userId);
        return "Successfully deleted " + userId;
    }
}
