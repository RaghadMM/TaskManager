package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.entities.Department;

import java.util.List;

public interface DepartmentService {
   Department addDepartment(Department department) ;
   Boolean assignManagerToDepartment(int departmentId,int managerId);
   String assignDepartmentMember(int departmentId, int managerId);
   List<Department> getAllDepartments();
}
