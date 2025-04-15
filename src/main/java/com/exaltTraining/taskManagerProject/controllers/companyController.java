package com.exaltTraining.taskManagerProject.controllers;

import com.exaltTraining.taskManagerProject.config.JwtService;
import com.exaltTraining.taskManagerProject.config.UserPrinted;
import com.exaltTraining.taskManagerProject.config.companyPrinted;
import com.exaltTraining.taskManagerProject.entities.Company;
import com.exaltTraining.taskManagerProject.entities.LoginRequest;
import com.exaltTraining.taskManagerProject.entities.User;
import com.exaltTraining.taskManagerProject.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("taskManager")
public class companyController {
    private CompanyService companyService;
    @Autowired
    private JwtService jwtService;
    public companyController(CompanyService companyService) {
        this.companyService = companyService;
    }


    //Create company API
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

    //Approve company account API
    @PutMapping("/companyApproval/{companyId}/{decision}")
    public String approveCompanyAccount(@PathVariable int companyId,@PathVariable String decision,@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String role = jwtService.extractUserRole(token);
        if (!"admin".equalsIgnoreCase(role)) {
            return "Unauthorized: Only admin can approve companies.";
        }
        Boolean isApproved= companyService.approveCompany(companyId,decision);
        System.out.println(isApproved);
        if(isApproved) {
            return "Company approved successfully";
        }
        else {
            return "Company not approved";
        }

    }

    //Companies log in API
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
    //Get all companies requests API
    @GetMapping("/companies")
    public List<companyPrinted> getCompanies(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String role = jwtService.extractUserRole(token);
        if (!"admin".equalsIgnoreCase(role)) {
            return null;
        }

        List<Company> companies=companyService.findAllCompanies();
        if(companies==null){
            return null;
        }
        List<companyPrinted> companyP = companies.stream()
                .map(company -> new companyPrinted(
                        company.getId(),
                        company.getName(),
                        company.getEmail(),
                        company.getApproved()
                ))
                .collect(Collectors.toList());

        return companyP;
    }

}
