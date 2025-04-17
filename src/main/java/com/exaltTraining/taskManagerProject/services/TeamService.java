package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.entities.Department;
import com.exaltTraining.taskManagerProject.entities.Team;
import com.exaltTraining.taskManagerProject.entities.User;

import java.util.List;

public interface TeamService {
    Team createTeam(Team team, Department department);
    Boolean assignTeamLeader(int teamId, int UserId);
    String assignTeamMember(int teamId, int UserId, int departmentId);
    List<Team> getAllTeams();
    String deleteTeam(int teamId, User departmentManager);
    Team getTeam(int teamId);
    List<User> getAvailableTeamMembers(User teamLeader);

}
