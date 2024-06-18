package com.Akashms.companyms.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @PostMapping("/company/createCompany")
    public ResponseEntity createCompany(@RequestBody Company company){
        companyService.createCompany(company);
        return new ResponseEntity("company created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/company/getCompanyById/{id}")
    public ResponseEntity getCompanyById(@PathVariable Long id){
        Company company=companyService.getCompanyById(id);

        if(company==null){
            return new ResponseEntity("Company with this id dose not present", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(company,HttpStatus.OK);
    }

    @GetMapping("/company/getAllCompanies")
    public ResponseEntity getAllCompanies(){
        List<Company> companies=companyService.getAllCompanies();

        return new ResponseEntity(companies,HttpStatus.OK);
    }

    @PutMapping("/company/updateCompany")
    public ResponseEntity updateCompany(@RequestParam Long id, @RequestBody Company company){
        boolean b=companyService.updateCompany(id,company);

        if(b==false){
            return new ResponseEntity("Company not present", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(true,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/company/deleteCompany/{id}")
    public ResponseEntity deleteCompany(@PathVariable Long id){
        boolean b=companyService.deleteCompanyById(id);
        return new ResponseEntity(b,HttpStatus.OK);
    }
}
