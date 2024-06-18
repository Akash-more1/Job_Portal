package com.Akashms.companyms.company;

import com.Akashms.companyms.company.dto.ReviewMessage;

import java.util.List;


public interface CompanyService {

    void createCompany( Company company);

    List<Company> getAllCompanies();

    Company getCompanyById(Long id);

    boolean updateCompany(Long id, Company company);

    boolean deleteCompanyById(Long id);

    public  void updateCompanyRating (ReviewMessage reviewMessage);

}
