package com.exaltTraining.taskManagerProject.controllers;

import com.exaltTraining.taskManagerProject.config.*;
import com.exaltTraining.taskManagerProject.entities.*;
import com.exaltTraining.taskManagerProject.services.CompanyService;
import com.exaltTraining.taskManagerProject.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/taskManager")
public class projectController {

    @Autowired
    private JwtService jwtService;
    private ProjectService projectService;
    private CompanyService companyService;
    public projectController(ProjectService projectService, CompanyService companyService) {
        this.projectService = projectService;
        this.companyService = companyService;
    }

    @PostMapping("/project/{departmentId}")
    public String addProject(@RequestBody Project project,@PathVariable int departmentId, @RequestHeader("Authorization") String authHeader){
        // Extract the token
        String token = authHeader.substring(7); // Remove "Bearer "

        // Extract role from token using JwtService
        String role = jwtService.extractUserRole(token);
        String companyEmail=jwtService.extractUsername(token);
        System.out.println(role);
        if (!"company".equalsIgnoreCase(role)) {
            return "Unauthorized: Only Companies accounts can add projects.";
        }
        Company company = companyService.findCompanyByEmail(companyEmail);
        if(company.getApproved()){
            project.setApproved(false);
            project.setCompany(company);

            Project newProject = projectService.addProject(project,departmentId);
            if(newProject != null){
                return "The project has been added successfully, Wait for the acceptance of this project";
            }
            else{
                return "The project has not been added successfully";
            }
        }
        else{
            return "The company is not approved yet";
        }


    }
    @GetMapping("/projectTasks/{projectId}")
    public List<taskPrinted> getProjectTasks(@PathVariable int projectId){
        List<Task> tasks = projectService.getProjectTasks(projectId);

        return tasks.stream().map(task -> {


            return new taskPrinted(task.getId(), task.getTitle(), task.getDescription(), task.getStatus().toString(), task.getDeadline());
        }).collect(Collectors.toList());
    }
    @GetMapping("/pendingProjects")
    public List<projectPrinted> getPendingProjects(@RequestHeader("Authorization") String authHeader ){
        // Extract the token
        String token = authHeader.substring(7); // Remove "Bearer "

        // Extract role from token using JwtService
        String role = jwtService.extractUserRole(token);
        System.out.println(role);
        if (!"admin".equalsIgnoreCase(role)) {
            return null;
        }
        List<Project> projects = projectService.getPendingProjects();
        if(projects == null){
            return null;
        }
        return projects.stream().map(project -> {
            //company
            Company company= project.getCompany();
            companyPrinted companyP=new companyPrinted(company.getId(),company.getName(),company.getEmail());
            System.out.println(companyP);
            //department
            Department department= project.getDepartment();
            System.out.println("department "+department.getId());
            DepartmentPrinted departmentP =new DepartmentPrinted(department.getId(),department.getName());
            System.out.println("department printed "+departmentP);

            return new projectPrinted(project.getId(),project.getTitle(),project.getDescription(),companyP,departmentP);
        }).collect(Collectors.toList());

    }
    @PutMapping("/project/{projectId}")
    public String approveProject(@PathVariable int projectId, @RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        String role = jwtService.extractUserRole(token);
        if (!"admin".equalsIgnoreCase(role)) {
            return null;
        }
        String result=projectService.approveProject(projectId);
        return result;
    }
}
