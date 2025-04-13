package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.dao.DepartmentRepository;
import com.exaltTraining.taskManagerProject.dao.UserRepository;
import com.exaltTraining.taskManagerProject.entities.Department;
import com.exaltTraining.taskManagerProject.entities.User;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository repository;
    private UserRepository userRepository;

    public DepartmentServiceImpl(DepartmentRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

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
            return true;

        }
        return false;
    }

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
}
