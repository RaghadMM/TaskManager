package com.exaltTraining.taskManagerProject.dao;


import com.exaltTraining.taskManagerProject.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
