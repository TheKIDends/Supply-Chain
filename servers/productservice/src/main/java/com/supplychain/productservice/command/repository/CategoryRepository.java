package com.supplychain.productservice.command.repository;

import com.supplychain.productservice.command.data.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    Category getCategoryByCategoryId(String CategoryId);
    Category findCategoriesByCategoryName(String categoryName);
    Category findByCategoryName(String categoryName);
    Category findByCategoryId(String categoryId);
}

