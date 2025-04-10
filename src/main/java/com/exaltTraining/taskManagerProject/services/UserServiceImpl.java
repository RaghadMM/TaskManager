package com.exaltTraining.taskManagerProject.services;
import com.exaltTraining.taskManagerProject.dao.UserRepository;
import com.exaltTraining.taskManagerProject.entities.User;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

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
    public User login(String email, String password) {

        List<User> users=userRepository.findAll();
        for(User user:users) {
            if(user.getEmail().equals(email) && bCryptPasswordEncoder.matches(password,user.getPassword())) {
                return user;
            }
        }
        System.out.println("User not found");
        return null; // or throw custom exception
    }
}
