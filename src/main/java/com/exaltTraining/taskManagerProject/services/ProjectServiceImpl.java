package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.dao.DepartmentRepository;
import com.exaltTraining.taskManagerProject.dao.ProjectRepository;
import com.exaltTraining.taskManagerProject.entities.Department;
import com.exaltTraining.taskManagerProject.entities.Project;
import com.exaltTraining.taskManagerProject.entities.Task;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public List<Task> getProjectTasks(int depId) {
        Optional<Project> tempProject= projectRepository.findById(depId);
        if(tempProject.isPresent()){
            List<Task> tasks=tempProject.get().getTasks();
            if(tasks.size()>0){
                return tasks;
            }
            else{
                return null;
            }
        }
        return null;

    }

    @Override
    public List<Project> getPendingProjects() {
        try{
            return projectRepository.getProjectsByApproved(false);

        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String approveProject(int projectId) {
        Optional<Project> tempProject= projectRepository.findById(projectId);
        if(tempProject.isPresent()){
            Project project=tempProject.get();
            project.setApproved(true);
            projectRepository.save(project);
            return "The project approved";
        }
        return "The project does not exist";
    }
}
