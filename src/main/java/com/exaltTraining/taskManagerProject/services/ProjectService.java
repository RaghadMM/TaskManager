package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.entities.Project;
import com.exaltTraining.taskManagerProject.entities.Task;

import java.util.List;

public interface ProjectService {
    Project addProject(Project project,int depId);
    List<Task> getProjectTasks(int depId);
    List<Project> getPendingProjects();


}
