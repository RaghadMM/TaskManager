package com.exaltTraining.taskManagerProject.dao;

import com.exaltTraining.taskManagerProject.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
