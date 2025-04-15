package com.exaltTraining.taskManagerProject.config;


//Helper class used to specify the form of returning project
public class projectPrinted {
    private int projectId;
    private String name;
    private String description ;
    private companyPrinted company;
    private DepartmentPrinted department;


    public projectPrinted(int projectId, String name, String description) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
    }

    public projectPrinted(int projectId, String name, String description, companyPrinted company, DepartmentPrinted department) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.company = company;
        this.department = department;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public companyPrinted getCompany() {
        return company;
    }

    public void setCompany(companyPrinted company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DepartmentPrinted getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentPrinted department) {
        this.department = department;
    }
}
