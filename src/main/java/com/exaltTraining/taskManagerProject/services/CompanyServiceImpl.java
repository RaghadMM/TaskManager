package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.dao.CompanyRepository;
import com.exaltTraining.taskManagerProject.dao.DepartmentRepository;
import com.exaltTraining.taskManagerProject.entities.Company;
import com.exaltTraining.taskManagerProject.entities.Department;
import com.exaltTraining.taskManagerProject.entities.Email;
import com.exaltTraining.taskManagerProject.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private CompanyRepository companyRepository;
    private EmailService emailService;

    public CompanyServiceImpl(CompanyRepository companyRepository, EmailService emailService) {
        this.companyRepository = companyRepository;
        this.emailService = emailService;
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
    public Boolean approveCompany(int companyId) {
        Optional<Company> tempCompany= companyRepository.findById(companyId);
        if(tempCompany.isPresent()){
            Company company = tempCompany.get();
            company.setApproved(true);
            companyRepository.save(company);
            Email email = new Email(company.getEmail(),"We are happy to be a collaborators with your company, your request is approved  \n waiting for projects!","Request approval");
            String result =emailService.sendSimpleMail(email);
            System.out.println(result);
            return true;
        }
        return false;
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
