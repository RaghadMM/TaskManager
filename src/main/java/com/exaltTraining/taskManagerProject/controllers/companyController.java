package com.exaltTraining.taskManagerProject.controllers;

import com.exaltTraining.taskManagerProject.config.JwtService;
import com.exaltTraining.taskManagerProject.entities.Company;
import com.exaltTraining.taskManagerProject.entities.LoginRequest;
import com.exaltTraining.taskManagerProject.entities.User;
import com.exaltTraining.taskManagerProject.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("taskManager")
public class companyController {
    private CompanyService companyService;
    @Autowired
    private JwtService jwtService;
    public companyController(CompanyService companyService) {
        this.companyService = companyService;
    }
    @PostMapping("/company")
    public String createCompanyAccount(@RequestBody Company company) {
        System.out.println(company.getPassword() + "passed");
        Company newCompany = companyService.createCompanyAccount(company);
        if(newCompany != null) {
            return "Comany added successfully";
        }
        else {
            return "Company creation failed";
        }
    }
    @PostMapping("/companyLogin")
    public String login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        Company authenticatedCompany =companyService.login(email,password);
        if(authenticatedCompany != null){
            String token = jwtService.generateToken(email,"company");
            return "Company logged in successfully \n Here is the token: " + token;
        }
        else{
            return "Company is not authenticated";
        }

    }

}
