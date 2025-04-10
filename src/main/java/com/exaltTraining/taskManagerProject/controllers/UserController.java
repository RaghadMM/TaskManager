package com.exaltTraining.taskManagerProject.controllers;

import com.exaltTraining.taskManagerProject.config.JwtService;
import com.exaltTraining.taskManagerProject.config.UserPrinted;
import com.exaltTraining.taskManagerProject.entities.LoginRequest;
import com.exaltTraining.taskManagerProject.entities.User;
import com.exaltTraining.taskManagerProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("taskManager")
public class UserController {

    private UserService userService;
    @Autowired
    private JwtService jwtService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public String createUser(@RequestBody User user) {
        try{
            userService.registerUser(user);
        }
        catch(Exception e){
            return "Error creating user: check for email validation ";
        }
        return "User registered successfully";
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        User authenticatedUser =userService.login(email,password);
        if(authenticatedUser != null){
            String token = jwtService.generateToken(email,authenticatedUser.getRole().toString());
            return "User logged in successfully \n Here is the token: " + token;
        }
        else{
            return "User is not authenticated";
        }
    }
    @GetMapping("/users")
    public List<UserPrinted> getUsers() {
        List <User> users= userService.getAllUsers();
        List<UserPrinted> userPrinteds = users.stream()
                .map(user -> new UserPrinted(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getRole().toString(),
                        user.getStatus().toString()// Assuming 'role' is a field in the User entity
                ))
                .collect(Collectors.toList());

        return userPrinteds;
    }



}
