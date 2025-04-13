package com.exaltTraining.taskManagerProject.controllers;

import com.exaltTraining.taskManagerProject.config.JwtService;
import com.exaltTraining.taskManagerProject.entities.Company;
import com.exaltTraining.taskManagerProject.entities.Project;
import com.exaltTraining.taskManagerProject.services.CompanyService;
import com.exaltTraining.taskManagerProject.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
