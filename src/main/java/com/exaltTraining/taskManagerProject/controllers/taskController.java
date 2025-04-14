package com.exaltTraining.taskManagerProject.controllers;

import com.exaltTraining.taskManagerProject.config.JwtService;
import com.exaltTraining.taskManagerProject.entities.Company;
import com.exaltTraining.taskManagerProject.entities.Task;
import com.exaltTraining.taskManagerProject.services.CompanyService;
import com.exaltTraining.taskManagerProject.services.TaskService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/taskManager")
public class taskController {

    @Autowired
    private JwtService jwtService;
    private TaskService taskService;
    private CompanyService companyService;

    public taskController(TaskService taskService, CompanyService companyService) {
        this.taskService = taskService;
        this.companyService = companyService;
    }

    @PostMapping("/task/project/{projectId}")
    public String addTask(@PathVariable int projectId, @RequestBody Task task,@RequestHeader("Authorization") String authHeader){
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
            String result=taskService.addTaskToAProject(task,projectId,company);
            if(result!=null){
                return result;
            }
            else{
                return "Task not added";
            }
        }
        else{
            return "The company is not approved yet.";
        }


    }
}
