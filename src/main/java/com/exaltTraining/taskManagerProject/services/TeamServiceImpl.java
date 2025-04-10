package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.dao.TeamRepository;
import com.exaltTraining.taskManagerProject.dao.UserRepository;
import com.exaltTraining.taskManagerProject.entities.Department;
import com.exaltTraining.taskManagerProject.entities.Team;
import com.exaltTraining.taskManagerProject.entities.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {

    private  TeamRepository teamRepository;
    private UserRepository userRepository;

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

    @Override
    public Boolean assignUserToTeam(int teamId, int UserId) {
        Optional<User> tempUser= userRepository.findById(UserId);
        Optional<Team> tempTeam= teamRepository.findById(teamId);
        if(tempUser.isPresent() && tempTeam.isPresent()){
            User user = tempUser.get();
            Team team = tempTeam.get();

            team.setTeamLeader(user);
            user.setRole(User.Role.TEAM_MANAGER);

            teamRepository.save(team);
            userRepository.save(user);
            return true;

        }
        return false;
    }
}
