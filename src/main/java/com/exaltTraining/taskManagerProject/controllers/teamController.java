package com.exaltTraining.taskManagerProject.controllers;

import com.exaltTraining.taskManagerProject.config.DepartmentPrinted;
import com.exaltTraining.taskManagerProject.config.JwtService;
import com.exaltTraining.taskManagerProject.config.UserPrinted;
import com.exaltTraining.taskManagerProject.config.teamPrinted;
import com.exaltTraining.taskManagerProject.dao.TeamRepository;
import com.exaltTraining.taskManagerProject.entities.Department;
import com.exaltTraining.taskManagerProject.entities.Team;
import com.exaltTraining.taskManagerProject.entities.User;
import com.exaltTraining.taskManagerProject.services.TeamService;
import com.exaltTraining.taskManagerProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    //Create new team API
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

        // Check if user is a department manager
        if (!"department_manager".equalsIgnoreCase(role)) {
            return "Unauthorized: Only department managers can add teams.";
        }
        Team newTeam = teamService.createTeam(team,department);
        if (newTeam != null) {
            return "Team created successfully";
        }
        return "Team creation failed";

    }

    //Set team leader API
    @PostMapping("/team/{teamId}/setLeader/{userId}")
    public String setLeader(@PathVariable int teamId, @PathVariable int userId, @RequestHeader("Authorization") String authHeader) {
        // Extract the token
        String token = authHeader.substring(7); // Remove "Bearer "

        // Extract role from token using JwtService
        String role = jwtService.extractUserRole(token);

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

    //Add team member API
    @PostMapping("/team/{teamId}/assignMember/{userId}")
    public String assignTeamMember(@PathVariable int teamId, @PathVariable int userId, @RequestHeader("Authorization") String authHeader) {
        // Extract the token
        String token = authHeader.substring(7); // Remove "Bearer "

        // Extract role from token using JwtService
        String username = jwtService.extractUsername(token);
        User user = userService.findUserByEmail(username); // the user who signed in
        String role = jwtService.extractUserRole(token);

        if (!"department_manager".equalsIgnoreCase(role)) {
            return "Unauthorized: Only department managers can assign to teams.";
        }
        //pass the signed-in user department to check if the team member department is the same
        String result= teamService.assignTeamMember(teamId, userId,user.getDepartment().getId());
        if(result!=null) {
            return result;
        }
        else {
            return "Cant assign Member to team";
        }
    }

    //Get all teams API
    @GetMapping("/teams")
    public List<teamPrinted> getAllTeams() {
        List<Team> teams = teamService.getAllTeams();
        return teams.stream().map(team -> {
            //team leader
            User manager = team.getTeamLeader();
            UserPrinted leader = new UserPrinted(manager.getId(), manager.getFirstName(),manager.getLastName(),manager.getEmail(),manager.getRole().toString(),manager.getStatus().toString());
            //department + department manager
            Department department = team.getDepartment();
            User depManager = department.getManager();
            UserPrinted departmentManager = new UserPrinted(depManager.getId(), depManager.getFirstName(),depManager.getLastName(),depManager.getEmail());
            DepartmentPrinted dep= new DepartmentPrinted(department.getId(),department.getName(),departmentManager);
            return new teamPrinted(team.getId(), team.getName(), leader,dep);
        }).collect(Collectors.toList());


    }

}
