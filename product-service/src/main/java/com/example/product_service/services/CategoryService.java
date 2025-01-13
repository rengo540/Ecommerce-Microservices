package com.example.product_service.services;


import com.example.product_service.exceptions.AlreadyExistsException;
import com.example.product_service.exceptions.ResourceNotFoundException;
import com.example.product_service.models.Category;
import com.example.product_service.repos.CategoryRepository;
import com.example.product_service.services.iservices.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).
                filter(c ->!categoryRepository.existsByName(c.getName())).
                map(categoryRepository::save).
                orElseThrow(()-> new AlreadyExistsException(category.getName()+" already exists"));

    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.findById(categoryId).ifPresentOrElse(categoryRepository::delete, ()-> {
            throw new ResourceNotFoundException("category not found");
            });
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll() ;
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("category not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name).
                orElseThrow(()->new ResourceNotFoundException("category not found with name "+ name));
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return categoryRepository.findById(id).
                map(existingCategory -> updateExistingCategory(category,existingCategory)).
                map(categoryRepository::save).
                orElseThrow(()->new ResourceNotFoundException("category not found"));

    }

    private Category updateExistingCategory(Category category,Category existingCategory){
        existingCategory.setName(category.getName());
        return existingCategory;
    }
}
