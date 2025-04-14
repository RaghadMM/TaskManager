package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.entities.Company;
import com.exaltTraining.taskManagerProject.entities.Task;

public interface TaskService {
    String addTaskToAProject(Task task, int projectId, Company company);
}
