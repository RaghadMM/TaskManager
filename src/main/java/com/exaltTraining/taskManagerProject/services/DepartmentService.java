package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.entities.Department;

public interface DepartmentService {
   Department addDepartment(Department department) ;
   Boolean assignManagerToDepartment(int departmentId,int managerId);
}
