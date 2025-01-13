package com.example.product_service.controllers;


import com.example.product_service.models.Category;
import com.example.product_service.response.ApiResponse;
import com.example.product_service.services.iservices.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @PostMapping()
    public ResponseEntity<ApiResponse> addCategory(@RequestParam String categoryName){
            Category category = new Category();
            category.setName(categoryName);
            Category addedCategory =categoryService.addCategory(category);
            return ResponseEntity.ok().body(new ApiResponse("successfully added",addedCategory));

    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllCategories(){
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("found",categories));

    }


    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId){
            Category category = categoryService.getCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse("found",category));

    }

    @GetMapping("/{categoryName}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable String categoryName){
            Category category = categoryService.getCategoryByName(categoryName);
            return ResponseEntity.ok(new ApiResponse("found",category));

    }

    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
            categoryService.deleteCategory(id);
            return  ResponseEntity.ok(new ApiResponse("deleted successfully", null));

    }

    @PutMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category) {

            Category updatedCategory = categoryService.updateCategory(category, id);
            return ResponseEntity.ok(new ApiResponse("Update success!", updatedCategory));
    }
}
