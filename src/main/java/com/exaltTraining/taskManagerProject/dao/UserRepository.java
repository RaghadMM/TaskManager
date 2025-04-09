package com.exaltTraining.taskManagerProject.dao;

import com.exaltTraining.taskManagerProject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
