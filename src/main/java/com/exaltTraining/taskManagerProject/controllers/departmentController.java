package com.exaltTraining.taskManagerProject.controllers;

import com.exaltTraining.taskManagerProject.config.DepartmentPrinted;
import com.exaltTraining.taskManagerProject.config.JwtService;
import com.exaltTraining.taskManagerProject.config.UserPrinted;
import com.exaltTraining.taskManagerProject.entities.Department;
import com.exaltTraining.taskManagerProject.entities.User;
import com.exaltTraining.taskManagerProject.services.DepartmentService;
import com.exaltTraining.taskManagerProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("taskManager")
public class departmentController {

    private DepartmentService departmentService;
    @Autowired
    private JwtService jwtService;

    public departmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    //Add a new department API
    @PostMapping("/department")
    public String addDepartment(@RequestBody Department department, @RequestHeader("Authorization") String authHeader) {
        // Extract the token
        String token = authHeader.substring(7); // Remove "Bearer "

        // Extract role from token using JwtService
        String role = jwtService.extractUserRole(token);

        System.out.println(role);
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

    //Set department manager API
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

    //Assign users to a department API
    @PutMapping("/department/{departmentId}/setDepartmentMember/{userId}")
    public String assignMemberToDepartment(@PathVariable int departmentId, @PathVariable int userId, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String role = jwtService.extractUserRole(token);
        if (!"admin".equalsIgnoreCase(role)) {
            return "Unauthorized: Only admin can assign members to department.";
        }
        String result = departmentService.assignDepartmentMember(departmentId, userId);
        if(result!=null) {
            return result;
        }
        else {
            return "Cant assign the member to department";
        }
    }
    //Get all departments API
    @GetMapping("/departments")
    public List<DepartmentPrinted> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return departments.stream().map(department -> {
            User manager = department.getManager();
            UserPrinted managerDTO = new UserPrinted(manager.getId(), manager.getFirstName(),manager.getLastName(),manager.getEmail(),manager.getRole().toString(),manager.getStatus().toString());
            return new DepartmentPrinted(department.getId(), department.getName(), managerDTO);
        }).collect(Collectors.toList());
    }

}
