package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.dao.CompanyRepository;
import com.exaltTraining.taskManagerProject.dao.DepartmentRepository;
import com.exaltTraining.taskManagerProject.entities.Company;
import com.exaltTraining.taskManagerProject.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company createCompanyAccount(Company company) {
        try{
            System.out.println(company.getPassword());
            company.setPassword(bCryptPasswordEncoder.encode(company.getPassword()));
            return companyRepository.save(company);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Company login(String email, String password) {
        List<Company> companies=companyRepository.findAll();
        for(Company company:companies) {
            if(company.getEmail().equals(email) && bCryptPasswordEncoder.matches(password,company.getPassword())) {
                return company;
            }
        }
        System.out.println("company not found");
        return null; // or throw custom exception
    }

    @Override
    public Company findCompanyByEmail(String email) {
        List<Company> companies=companyRepository.findAll();
        for(Company company:companies) {
            if(company.getEmail().equals(email)) {
                return company;
            }
        }
        System.out.println("company not found");
        return null;
    }
}
