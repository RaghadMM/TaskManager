package com.exaltTraining.taskManagerProject.controllers;

import com.exaltTraining.taskManagerProject.config.JwtService;
import com.exaltTraining.taskManagerProject.dao.TeamRepository;
import com.exaltTraining.taskManagerProject.entities.Department;
import com.exaltTraining.taskManagerProject.entities.Team;
import com.exaltTraining.taskManagerProject.entities.User;
import com.exaltTraining.taskManagerProject.services.TeamService;
import com.exaltTraining.taskManagerProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/taskManager")
public class teamController {

    private TeamService teamService;
   @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    public teamController(JwtService jwtService, TeamService teamService) {
        this.jwtService = jwtService;
        this.teamService = teamService;
    }

    @PostMapping("/team")
    public String createTeam(@RequestBody Team team, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7); // Remove "Bearer "

        // Extract role from token using JwtService
        String role = jwtService.extractUserRole(token);
        String username = jwtService.extractUsername(token);
        User user = userService.findUserByEmail(username);
        Department department=user.getDepartment();
        System.out.println(username);
        System.out.println("dep "+department);

        // Check if user is admin
        if (!"department_manager".equalsIgnoreCase(role)) {
            return "Unauthorized: Only department managers can add teams.";
        }
        Team newTeam = teamService.createTeam(team,department);
        if (newTeam != null) {
            return "Team created successfully";
        }
        return "Team creation failed";

    }
    @PostMapping("/team/{teamId}/setLeader/{userId}")
    public String setLeader(@PathVariable int teamId, @PathVariable int userId, @RequestHeader("Authorization") String authHeader) {
        // Extract the token
        String token = authHeader.substring(7); // Remove "Bearer "

        // Extract role from token using JwtService
        String role = jwtService.extractUserRole(token);

        // Check if user is admin
        if (!"department_manager".equalsIgnoreCase(role)) {
            return "Unauthorized: Only department managers can assign to teams.";
        }

        Boolean added= teamService.assignTeamLeader(teamId, userId);
        if(added) {
            return "Leader assigned successfully";
        }
        else {
            return "Cant assign Leader to team";
        }
    }
}
