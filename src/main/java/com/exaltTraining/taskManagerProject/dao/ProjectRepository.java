package com.exaltTraining.taskManagerProject.dao;

import com.exaltTraining.taskManagerProject.entities.Department;
import com.exaltTraining.taskManagerProject.entities.Project;
import com.exaltTraining.taskManagerProject.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> getProjectsByApproved(Boolean approved);

    Project findProjectByTasks(List<Task> tasks);
}
