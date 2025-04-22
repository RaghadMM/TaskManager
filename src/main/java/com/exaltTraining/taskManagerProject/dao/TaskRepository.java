package com.exaltTraining.taskManagerProject.dao;


import com.exaltTraining.taskManagerProject.entities.Project;
import com.exaltTraining.taskManagerProject.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query("SELECT t FROM Task t WHERE " +
            "t.title LIKE CONCAT('%',:query, '%')" +
            "Or t.description LIKE CONCAT('%', :query, '%')")
    List<Task> searchTasks (String query);

    List<Task> getTasksByStatus(Task.Status status);
}
