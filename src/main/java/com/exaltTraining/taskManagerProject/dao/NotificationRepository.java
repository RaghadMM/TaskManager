package com.exaltTraining.taskManagerProject.dao;


import com.exaltTraining.taskManagerProject.entities.Notification;
import com.exaltTraining.taskManagerProject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    @Query("SELECT n FROM Notification n WHERE n.body LIKE %:taskTitle% AND n.user = :user")
    List<Notification> findByTaskTitleAndUser(@Param("taskTitle") String taskTitle, @Param("user") User user);

}
