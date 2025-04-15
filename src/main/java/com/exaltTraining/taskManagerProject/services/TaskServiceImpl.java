package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.dao.ProjectRepository;
import com.exaltTraining.taskManagerProject.dao.TaskRepository;
import com.exaltTraining.taskManagerProject.entities.Company;
import com.exaltTraining.taskManagerProject.entities.Department;
import com.exaltTraining.taskManagerProject.entities.Project;
import com.exaltTraining.taskManagerProject.entities.Task;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private ProjectRepository projectRepository;

    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    //Add new task to a project
    //The adding process is restricted to be done just by the company that owns the project
    @Override
    public String addTaskToAProject(Task task, int projectId, Company company) {
        Optional<Project> tempProject= projectRepository.findById(projectId);
        if(tempProject.isPresent()){
            Project project = tempProject.get();
            if(project.getCompany().equals(company)){
                try{
                    task.setProject(project);
                     taskRepository.save(task);
                     return "Task added successfully";
                }
                catch(Exception e){
                    e.printStackTrace();
                    return null;
                }
            }
            else{
                return "This company doesn't have the required project";
            }

        }
        return "The project doesn't exist";
    }
}
