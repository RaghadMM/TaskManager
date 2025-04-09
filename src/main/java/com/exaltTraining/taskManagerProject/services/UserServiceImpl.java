package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.dao.UserRepository;
import com.exaltTraining.taskManagerProject.entities.User;
import jakarta.persistence.EntityManager;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    //private AuthenticationManager authenticationManager;
    private EntityManager entityManager;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User registerUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        return user;

    }

    @Override
    public Boolean login(String email, String password) {

        System.out.println(email);
        System.out.println("pass"+password);
        List<User> users=userRepository.findAll();
        for(User user:users) {
            System.out.println(user.getEmail());
            System.out.println(user.getPassword());
            System.out.println(bCryptPasswordEncoder.encode(password));
            if(user.getEmail().equals(email) && bCryptPasswordEncoder.matches(password,user.getPassword())) {

                return true;
            }
        }
        System.out.println("User not found");
        return false; // or throw custom exception


    }
}
