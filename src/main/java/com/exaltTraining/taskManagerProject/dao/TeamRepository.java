package com.exaltTraining.taskManagerProject.dao;

import com.exaltTraining.taskManagerProject.entities.Department;
import com.exaltTraining.taskManagerProject.entities.Team;
import com.exaltTraining.taskManagerProject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Integer> {
    Team findTeamByTeamLeader(User teamLeader);
}
