package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.dao.DepartmentRepository;
import com.exaltTraining.taskManagerProject.dao.ProjectRepository;
import com.exaltTraining.taskManagerProject.entities.Department;
import com.exaltTraining.taskManagerProject.entities.Project;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;
    private DepartmentRepository departmentRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, DepartmentRepository departmentRepository) {
        this.projectRepository = projectRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Project addProject(Project project, int depId) {
        Optional<Department> tempDepartment= departmentRepository.findById(depId);
        if(tempDepartment.isPresent()){
            Department department = tempDepartment.get();
            try{
                project.setDepartment(department);
                return projectRepository.save(project);
            }
            catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }
        return null;

    }
}
