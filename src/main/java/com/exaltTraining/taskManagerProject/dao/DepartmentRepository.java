package com.exaltTraining.taskManagerProject.dao;

import com.exaltTraining.taskManagerProject.entities.Department;
import com.exaltTraining.taskManagerProject.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    @Query("SELECT d FROM Department d WHERE " +
            "d.name LIKE CONCAT('%',:query, '%')" )
    List<Department> searchDepartments(String query);
}
