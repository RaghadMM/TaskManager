package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.dao.TeamRepository;
import com.exaltTraining.taskManagerProject.entities.Department;
import com.exaltTraining.taskManagerProject.entities.Team;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements TeamService {

    private  TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public Team createTeam(Team team, Department department) {
        try{
            System.out.println(department);
            team.setDepartment(department);
            teamRepository.saveAndFlush(team);
            return team;
        }
        catch(Exception e){
            return null;
        }
    }
}
