package com.exaltTraining.taskManagerProject.controllers;

import com.exaltTraining.taskManagerProject.config.JwtService;
import com.exaltTraining.taskManagerProject.config.UserPrinted;
import com.exaltTraining.taskManagerProject.config.companyPrinted;
import com.exaltTraining.taskManagerProject.config.taskPrinted;
import com.exaltTraining.taskManagerProject.entities.*;
import com.exaltTraining.taskManagerProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    // Create an account for a user by the admin API
    @PostMapping("/user")
    public String createUser(@RequestBody User user, @RequestHeader("Authorization") String authHeader) {
        try{
            //Extract the user role
            String token = authHeader.substring(7);
            String role = jwtService.extractUserRole(token);
            if (!"admin".equalsIgnoreCase(role)) {
                return "Unauthorized: Only admin can register users.";
            }
            userService.registerUser(user);
        }
        catch(Exception e){
            e.printStackTrace();
            return "Error creating user: check for email validation ";
        }
        return "User registered successfully";
    }

    //Log in for the users API
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

    //Get all users API
    @GetMapping("/users")
    public List<UserPrinted> getUsers() {
        List <User> users= userService.getAllUsers();
        return printUser(users);
    }

    //Reset password API
    @PutMapping("/passwordReset/{userId}")
    public String changePassword(@PathVariable int userId, @RequestBody PasswordResetForm form, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String email = jwtService.extractUsername(token);

        return userService.resetPassword(userId, email,form);

    }
    @GetMapping("/users/search")
    public ResponseEntity<List<UserPrinted>> searchUsers(@RequestParam("query") String query, @RequestHeader ("Authorization") String authHeader ) {
        String token = authHeader.substring(7);
        String role = jwtService.extractUserRole(token);
        if (!"admin".equalsIgnoreCase(role)) {
            return null;
        }
        List<User> users = userService.searchUsers(query);

        return ResponseEntity.ok(printUser(users));
    }

    // A helper function to form the list of users returned
    private List<UserPrinted> printUser(List <User> users) {

        List<UserPrinted> userPrinteds = users.stream()
                .map(user -> new UserPrinted(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getRole().toString(),
                        user.getStatus().toString()
                ))
                .collect(Collectors.toList());

        return userPrinteds;

    }
}
