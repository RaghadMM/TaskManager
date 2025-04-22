package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.dao.ProjectRepository;
import com.exaltTraining.taskManagerProject.dao.TaskRepository;
import com.exaltTraining.taskManagerProject.dao.TeamRepository;
import com.exaltTraining.taskManagerProject.dao.UserRepository;
import com.exaltTraining.taskManagerProject.entities.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskServiceImpl implements TaskService {

    private final TeamRepository teamRepository;
    private TaskRepository taskRepository;
    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository, TeamRepository teamRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    //Add new task to a project
    //The adding process is restricted to be done just by the company that owns the project
    @Override
    public String addTaskToAProject(Task task, int projectId, Company company) {
        Optional<Project> tempProject= projectRepository.findById(projectId);
        if(tempProject.isPresent()){
            Project project = tempProject.get();
            if(project.getCompany().equals(company)){
                try{
                    task.setProject(project);
                     taskRepository.save(task);
                     return "Task added successfully";
                }
                catch(Exception e){
                    e.printStackTrace();
                    return null;
                }
            }
            else{
                return "This company doesn't have the required project";
            }

        }
        return "The project doesn't exist";
    }

    //Assign a task to a specific team member by the team leader
    //Check for user role, availability and team matching
    //Check for task pending
    @Override
    public String assignTaskToAMember(int taskId, int memberId, User teamLeader) {
        Team theTeam = teamRepository.findTeamByTeamLeader(teamLeader);
        Task task = taskRepository.findById(taskId).get();
        User member = userRepository.findById(memberId).get();
        Project project = task.getProject();
        if(project.getAssignedTeam().equals(theTeam)){
            if(member.getRole()== User.Role.EMPLOYEE){
                if(member.getStatus()== User.Status.Available){
                    if(member.getTeam()==theTeam){
                        if(task.getStatus()== Task.Status.TODO){
                            task.setAssignedUser(member);
                            task.setStatus(Task.Status.IN_PROGRESS);
                            member.setStatus(User.Status.Busy);
                            taskRepository.save(task);
                            userRepository.save(member);
                            return "Task assigned successfully";
                        }
                        else{
                            return "The task is not pending";
                        }

                    }
                    else{
                        return "Ths member is not assigned to your team";
                    }
                }
                else{
                    return "The user is not available";
                }

            }
            else{
                return "This user is not an employee";
            }

        }
        else{
            return "This task is not assigned to this team projects";
        }
    }

    //get the team pending tasks by the team leader
    @Override
    public List<Task> getTeamBendingTasks(User teamLeader) {
        Team team = teamRepository.findTeamByTeamLeader(teamLeader);
        List<Project> projects = team.getProjects();
        List<Task> tasks = new ArrayList<>();
        for(Project project : projects){
            List<Task> tempTasks = project.getTasks();
            for(Task task : tempTasks){
                if(task.getStatus() == Task.Status.TODO){
                    tasks.add(task);
                }
            }
        }
        tasks.sort(Comparator.comparing(Task::getDeadline));
        return tasks;
    }

    //Delete a task by its project company
    //The task must be pending to delete it
    @Override
    public String deleteTaskFromAProject(int taskId, Company company) {
        Task task = taskRepository.findById(taskId).get();
        Project project = task.getProject();
        if(project.getCompany().equals(company)){
            if(task.getStatus()== Task.Status.TODO){
                taskRepository.delete(task);
                return "Task deleted successfully";
            }
            else{
                return "The task is not pending, it cant be deleted";
            }

        }
        else{
            return "This company doesn't have the required project";
        }

    }

    //Mark a task as done by his employee
    @Override
    public String updateTaskStatus(int taskId, User employee) {
        Task theTask = taskRepository.findById(taskId).get();
        if(theTask.getAssignedUser().equals(employee)){
            theTask.setStatus(Task.Status.DONE);
            employee.setStatus(User.Status.Available);
            taskRepository.save(theTask);
            userRepository.save(employee);
            return "Task updated successfully";
        }
        else{
            return "The task is not assigned to this employee";
        }
    }

    @Override
    public List<Task> searchTasks(String query) {
        return taskRepository.searchTasks(query);
    }

    //Get tasks by their status
    //Done tasks can be retrieved by their employee, or by QA employee to check them
    //Reviewed task can be retrieved by their employee
    @Override
    public List<Task> getTasksByStatus(User user, String status) {
        Department userDepartment = user.getDepartment();

        List <Task> returnedTasks = new ArrayList<>();
        //DONE
        if(status.equals("DONE")){
            List<Task> doneTasks= taskRepository.getTasksByStatus(Task.Status.DONE);
            if(userDepartment.getType()== Department.Type.QA){
                for(Task task : doneTasks){
                    if(task.getProject().getDepartment().getType()== Department.Type.SOFTWARE){
                        returnedTasks.add(task);
                    }
                }
            }
            else{
                for(Task task : doneTasks){
                    if(task.getAssignedUser().equals(user)){
                        returnedTasks.add(task);
                    }
                }
            }
        }
        else if(status.equals("REVIEWED")){
            List<Task> doneTasks= taskRepository.getTasksByStatus(Task.Status.REVIEWED);
            if(userDepartment.getType()== Department.Type.SOFTWARE){
                for(Task task : doneTasks){
                    if(task.getAssignedUser().equals(user)){
                        returnedTasks.add(task);
                    }
                }
            }
            else{
                return null;
            }

        }
        else{
            return null;
        }

        return returnedTasks;
    }

}
