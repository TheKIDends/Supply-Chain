package com.supplychain.productservice.command.repository;

import com.supplychain.productservice.command.data.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

}

