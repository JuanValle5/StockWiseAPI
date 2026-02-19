package com.stockwise.controller;


import com.stockwise.dto.CategoryDTO;
import com.stockwise.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final ICategoryService categoryService;

    @Autowired
    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(categoryService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createdCategory(@RequestBody CategoryDTO categoryDTO){

        CategoryDTO categoryDTO1 = categoryService.save(categoryDTO);
        return ResponseEntity
                .created(URI.create("/api/category/create/" + categoryDTO1.getId()))
                .body(categoryDTO1);
    }



}
