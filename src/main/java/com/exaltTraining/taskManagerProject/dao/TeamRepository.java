package com.exaltTraining.taskManagerProject.dao;

import com.exaltTraining.taskManagerProject.entities.Department;
import com.exaltTraining.taskManagerProject.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}
