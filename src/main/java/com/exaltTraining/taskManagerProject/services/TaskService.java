package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.entities.Company;
import com.exaltTraining.taskManagerProject.entities.Task;
import com.exaltTraining.taskManagerProject.entities.User;

import java.util.List;

public interface TaskService {
    String addTaskToAProject(Task task, int projectId, Company company);
    String assignTaskToAMember(int taskId, int memberId, User teamLeader);
    List<Task> getTeamBendingTasks(User teamLeader);
    String deleteTaskFromAProject(int taskId, Company company);
    String updateTaskStatus(int taskId, User employee);
    List<Task> searchTasks(String query);
    List<Task> getTasksByStatus(User user,String status);
    void checkUpcomingDeadlines();
    String taskCount();
}
