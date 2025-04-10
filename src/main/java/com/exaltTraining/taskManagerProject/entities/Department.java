package com.exaltTraining.taskManagerProject.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="department_id")
    private List<User> tasks;

    @OneToOne
    @JoinColumn(name="manager_id")
    private User manager;

    @OneToMany
    @JoinColumn(name="department_id")
    private List<Team> teams;

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getTasks() {
        return tasks;
    }

    public void setTasks(List<User> tasks) {
        this.tasks = tasks;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tasks=" + tasks +
                ", manager=" + manager +
                '}';
    }
}
