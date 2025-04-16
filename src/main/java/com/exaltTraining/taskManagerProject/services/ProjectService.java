package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.entities.Company;
import com.exaltTraining.taskManagerProject.entities.Project;
import com.exaltTraining.taskManagerProject.entities.Task;
import com.exaltTraining.taskManagerProject.entities.Team;

import java.util.List;

public interface ProjectService {
    String addProject(Project project,int depId,Company company);
    List<Task> getProjectTasks(int depId);
    List<Project> getPendingProjects();
    //String approveProject(int projectId);
    Boolean checkForProjectAvailability(int departmentId, Project project, Company company,Boolean isDelay);
    String cancelOrDelayProject(int projectId, String decision, Company company);
    List<Project> getCompanyPendingProjects(Company company);


}
