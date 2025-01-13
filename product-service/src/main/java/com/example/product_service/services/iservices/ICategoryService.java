package com.example.product_service.services.iservices;



import com.example.product_service.models.Category;

import java.util.List;

public interface ICategoryService {

    Category addCategory(Category category);
    void deleteCategory(Long categoryId);
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    Category updateCategory(Category category, Long id);

}
