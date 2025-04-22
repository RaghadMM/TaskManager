package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.entities.Notification;
import com.exaltTraining.taskManagerProject.entities.User;

import java.util.List;

public interface NotificationService {

    List<Notification> getNotifications(User user, String required);
    String markAsRead(int notificationId, User user);
}
