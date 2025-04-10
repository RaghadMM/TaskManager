package com.exaltTraining.taskManagerProject.controllers;

import com.exaltTraining.taskManagerProject.config.JwtService;
import com.exaltTraining.taskManagerProject.entities.Department;
import com.exaltTraining.taskManagerProject.services.DepartmentService;
import com.exaltTraining.taskManagerProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("taskManager")
public class departmentController {

    private DepartmentService departmentService;
    @Autowired
    private JwtService jwtService;

    public departmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/department")
    public String addDepartment(@RequestBody Department department, @RequestHeader("Authorization") String authHeader) {
        // Extract the token
        String token = authHeader.substring(7); // Remove "Bearer "

        // Extract role from token using JwtService
        String role = jwtService.extractUserRole(token);

        // Check if user is admin
        if (!"admin".equalsIgnoreCase(role)) {
            return "Unauthorized: Only admin can add departments.";
        }

        Department tempDep=departmentService.addDepartment(department);
        if(tempDep!=null) {
            return "Department added successfully";
        }
        else {
            return "Department not added";
        }

    }
    @PostMapping("/department/{departmentId}/setManager/{userId}")
    public String addDepartmentManager(@PathVariable int departmentId,@PathVariable int userId, @RequestHeader("Authorization") String authHeader) {
        // Extract the token
        String token = authHeader.substring(7); // Remove "Bearer "

        // Extract role from token using JwtService
        String role = jwtService.extractUserRole(token);

        // Check if user is admin
        if (!"admin".equalsIgnoreCase(role)) {
            return "Unauthorized: Only admin can add departments.";
        }

        Boolean added= departmentService.assignManagerToDepartment(departmentId, userId);
        if(added) {
            return "Manager assigned successfully";
        }
        else {
            return "Cant assign the manager to department";
        }

    }

}
