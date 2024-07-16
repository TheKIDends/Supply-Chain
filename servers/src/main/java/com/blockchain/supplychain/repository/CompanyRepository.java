package com.blockchain.supplychain.repository;

import com.blockchain.supplychain.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, String> {

    Company findByUsername(String username);
    Company findCompanyById(String id);
}

