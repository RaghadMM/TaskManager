package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.entities.Department;
import com.exaltTraining.taskManagerProject.entities.Team;

public interface TeamService {
    Team createTeam(Team team, Department department);
}
