package com.exaltTraining.taskManagerProject.config;

public class companyPrinted {
    private int companyId;
    private String companyName;
    private String companyEmail;

    public companyPrinted(int companyId, String companyName, String companyEmail) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyEmail = companyEmail;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }
}
