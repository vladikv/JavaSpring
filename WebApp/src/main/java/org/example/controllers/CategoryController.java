//package org.example.controllers;
//
//import lombok.RequiredArgsConstructor;
//import org.example.dto.category.CategoryCreateDTO;
//import org.example.dto.category.CategoryItemDTO;
//import org.example.entities.CategoryEntity;
//import org.example.mappers.CategoryMapper;
//import org.example.repositories.CategoryRepository;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//public class CategoryController {
//    private final CategoryRepository categoryRepository;
//    private final CategoryMapper categoryMapper;
//    @GetMapping("/")
//    public ResponseEntity<List<CategoryItemDTO>> index() {
//        var result = categoryMapper.listCategoriesToListCategoryItemDTO(categoryRepository.findAll());
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    @PostMapping("/api/category")
//    public CategoryEntity create(@RequestBody CategoryCreateDTO dto){
//        CategoryEntity entity = new CategoryEntity();
//        entity.setName(dto.getName());
//        entity.setDescription(dto.getDescription());
//        categoryRepository.save(entity);
//        return entity;
//    }
//}
package org.example.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.category.CategoryCreateDTO;
import org.example.dto.category.CategoryItemDTO;
import org.example.dto.category.CategoryUpdateDTO;
import org.example.entities.CategoryEntity;
import org.example.mappers.CategoryMapper;
import org.example.repositories.CategoryRepository;
import org.example.storage.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final StorageService storageService;

    @GetMapping("/")
    public ResponseEntity<List<CategoryItemDTO>> index() {
        var result = categoryMapper.listCategoriesToListCategoryItemDTO(categoryRepository.findAll());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/api/category", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CategoryEntity create(@Valid @ModelAttribute CategoryCreateDTO dto){
        var filename = storageService.saveMultipartFile(dto.getImage());
        CategoryEntity entity = categoryMapper.createCategoryToCategory(dto);
        entity.setImage(filename);
        categoryRepository.save(entity);
        return entity;
    }


    @GetMapping("/api/category/{id}")
    public ResponseEntity<CategoryItemDTO> getById(@PathVariable int id) {
        var catOptional = categoryRepository.findById(id);
        if(catOptional.isPresent()){
            var result = categoryMapper.categoryToCategoryItemDTO(catOptional.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/api/category/{id}",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryItemDTO> UpdateCategory(@PathVariable int id, @ModelAttribute CategoryUpdateDTO dto) {
        var catOptional = categoryRepository.findById(id);
        if(!catOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        var category = catOptional.get();
        if(category.getImage() != null && !category.getImage().isEmpty()){
            storageService.removeFile(category.getImage());
        }
        var filename = storageService.saveMultipartFile(dto.getImage());

        category = categoryMapper.updateCategoryToCategory(dto);
        category.setId(catOptional.get().getId());
        //category.setName(dto.getName());
        //category.setDescription(dto.getDescription());
        category.setImage(filename);
        categoryRepository.save(category);

        var result = categoryMapper.categoryToCategoryItemDTO(category);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @DeleteMapping(value = "/api/category/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int id) {
        var catOptional = categoryRepository.findById(id);
        if(catOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var category = catOptional.get();
        if(category.getImage() != null && !category.getImage().isEmpty()){
            storageService.removeFile(category.getImage());
        }

        categoryRepository.delete(category);
        return ResponseEntity.noContent().build();
    }

}