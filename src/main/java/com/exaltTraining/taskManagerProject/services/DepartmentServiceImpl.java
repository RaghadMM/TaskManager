package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.dao.DepartmentRepository;
import com.exaltTraining.taskManagerProject.dao.NotificationRepository;
import com.exaltTraining.taskManagerProject.dao.UserRepository;
import com.exaltTraining.taskManagerProject.entities.Department;
import com.exaltTraining.taskManagerProject.entities.Notification;
import com.exaltTraining.taskManagerProject.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository repository;
    private UserRepository userRepository;
    private NotificationRepository notificationRepository;

    public DepartmentServiceImpl(DepartmentRepository repository, UserRepository userRepository, NotificationRepository notificationRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    //Add a new department by the admin
    @Override
    public Department addDepartment(Department department) {
        try {
            System.out.println(department);
            return repository.save(department);
        }
        catch (Exception e) {
            System.out.println("cant add department");
            System.out.println(e.getMessage());
            return null;
        }
    }

    //Set a user as a department manager by the admin
    @Override
    public Boolean assignManagerToDepartment(int departmentId, int managerId) {
        Optional<User> tempUser= userRepository.findById(managerId);
        Optional<Department> tempDepartment= repository.findById(departmentId);
        if(tempUser.isPresent() && tempDepartment.isPresent()){
            User user = tempUser.get();
            Department department = tempDepartment.get();

            department.setManager(user);
            user.setRole(User.Role.DEPARTMENT_MANAGER);
            user.setDepartment(department);
            repository.save(department);
            userRepository.save(user);
            notificationRepository.save(new Notification("Department Manager Assigned", "You are assigned as a department manager for " +
                    department.getName() +
                    "department", false, user));
            return true;

        }
        return false;
    }

    //Assign users to specific department by the admin
    @Override
    public String assignDepartmentMember(int departmentId, int managerId) {
        Optional<User> tempUser= userRepository.findById(managerId);
        Optional<Department> tempDepartment= repository.findById(departmentId);
        if(tempUser.isPresent() && tempDepartment.isPresent()){
            User user = tempUser.get();
            Department department = tempDepartment.get();

            if(user.getDepartment() == null){
                user.setDepartment(department);
                userRepository.save(user);
                return "The user has been assigned to the department";
            }
            else{
                return "The user is already assigned to a department";

            }
        }
        return null;
    }

    //Get all company departments
    @Override
    public List<Department> getAllDepartments() {
        return repository.findAll();
    }

    //Delete a department
    //Cascade delete, delete all department teams
    @Override
    public Boolean deleteDepartment(int departmentId) {
        try{
            repository.deleteById(departmentId);
            return true;
        }
        catch (Exception e) {
            System.out.println("cant delete department");
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Department> searchDepartments(String query) {
        List<Department> departments=repository.searchDepartments(query);
        return departments;
    }

    @Override
    public String departmentCount() {
        return "The total number of departments is " + repository.count() + " departments \n";
    }

}
