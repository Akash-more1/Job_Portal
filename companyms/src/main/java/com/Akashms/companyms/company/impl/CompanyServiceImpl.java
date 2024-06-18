package com.Akashms.companyms.company.impl;


import com.Akashms.companyms.company.Company;
import com.Akashms.companyms.company.CompanyRepository;
import com.Akashms.companyms.company.CompanyService;

import com.Akashms.companyms.company.clients.ReviewClient;
import com.Akashms.companyms.company.dto.ReviewMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {


    private CompanyRepository companyRepository;

    private ReviewClient reviewClient;



    public CompanyServiceImpl( CompanyRepository companyRepository, ReviewClient reviewClient) {

        this.companyRepository = companyRepository;
        this.reviewClient=reviewClient;
    }

    @Override
    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(Long id) {

            Company company=companyRepository.findById(id).orElse(null);
            return company;


    }

    @Override
    public boolean updateCompany(Long id, Company company) {
        if(getCompanyById(id)==null){
            return false;
        }
        Company comp =getCompanyById(id);

        comp.setName(company.getName());
        comp.setDescription(company.getDescription());

        companyRepository.save(comp);
        return true;
    }

    @Override
    public boolean deleteCompanyById(Long id) {
        companyRepository.deleteById(id);
        return true;
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
        Company company=getCompanyById(reviewMessage.getCompanyId());

        double averageRating=reviewClient.getAverageRatingOfCompany(reviewMessage.getCompanyId());
        System.out.println(reviewMessage.getDescription()+""+" "+averageRating);
        company.setAverageRating(averageRating);

        companyRepository.save(company);


    }
}
