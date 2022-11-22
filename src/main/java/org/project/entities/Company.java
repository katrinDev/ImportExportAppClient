package org.project.entities;

import java.util.Objects;

public class Company {

    private int companyId;

    private String companyName;

    private String country;

    private String checkingAccount;

    private String companyEmail;

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCheckingAccount() {
        return checkingAccount;
    }

    public void setCheckingAccount(String checkingAccount) {
        this.checkingAccount = checkingAccount;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return companyId == company.companyId && Objects.equals(companyName, company.companyName) && Objects.equals(country, company.country) && Objects.equals(checkingAccount, company.checkingAccount) && Objects.equals(companyEmail, company.companyEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, companyName, country, checkingAccount, companyEmail);
    }
}
