package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.dao.TeamRepository;
import com.exaltTraining.taskManagerProject.dao.UserRepository;
import com.exaltTraining.taskManagerProject.entities.Department;
import com.exaltTraining.taskManagerProject.entities.Team;
import com.exaltTraining.taskManagerProject.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {

    private TeamRepository teamRepository;
    private UserRepository userRepository;

    public TeamServiceImpl(TeamRepository teamRepository,UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
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
    public Boolean assignTeamLeader(int teamId, int UserId) {
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

    @Override
    public String assignTeamMember(int teamId, int UserId, int departmentId) {
        Optional<User> tempUser= userRepository.findById(UserId);
        Optional<Team> tempTeam= teamRepository.findById(teamId);
        if(tempUser.isPresent() && tempTeam.isPresent()) {
            User user = tempUser.get();
            Team team = tempTeam.get();
            //check for department matching and not assigned to a team
            if(user.getDepartment()!=null){
                System.out.println("in null department");
                if(user.getDepartment().getId() == departmentId) {
                    if (user.getTeam() == null) {
                        user.setTeam(team);
                        userRepository.save(user);
                        return "The user has been assigned to the team";

                    } else {
                        return "The user is already assigned to a team";
                    }
                }
                else{
                    return "The User is from another department!";
                }
            }
            else{
                return "The User is not assigned to a department yet!";
            }


        }
        return null;


    }

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }
}
