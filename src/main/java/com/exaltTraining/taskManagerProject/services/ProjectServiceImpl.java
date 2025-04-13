package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.dao.ProjectRepository;
import com.exaltTraining.taskManagerProject.entities.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project addProject(Project project) {
        try{
            return projectRepository.save(project);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
