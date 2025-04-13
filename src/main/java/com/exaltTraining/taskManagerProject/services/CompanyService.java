package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.entities.Company;

public interface CompanyService {
    Company createCompanyAccount(Company company);
    Company login(String email, String password);
}
