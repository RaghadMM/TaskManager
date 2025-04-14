package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.entities.Company;

public interface CompanyService {
    Company createCompanyAccount(Company company);
    Boolean approveCompany(int companyId);
    Company login(String email, String password);
    Company findCompanyByEmail(String email);
}
