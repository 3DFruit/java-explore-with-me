package ru.practicum.explorewithme.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.model.category.CategoryDto;
import ru.practicum.explorewithme.model.category.NewCategoryDto;
import ru.practicum.explorewithme.service.category.CategoryService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/admin/categories")
public class AdminCategoriesController {
    CategoryService service;

    @Autowired
    public AdminCategoriesController(CategoryService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto postCategory(@Valid @RequestBody NewCategoryDto categoryDto) {
        log.trace("Создание категории: {}", categoryDto);
        return service.createCategory(categoryDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long catId) {
        log.trace("Удаление категории с id {}", catId);
        service.deleteCategory(catId);
    }

    @PatchMapping("/{catId}")
    public CategoryDto patchCategory(@PathVariable Long catId, @RequestBody CategoryDto dto) {
        log.trace("Обновление категории с id {}", catId);
        return service.patchCategory(catId, dto);
    }
}
