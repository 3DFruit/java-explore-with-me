package ru.practicum.explorewithme.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.model.CustomPageRequest;
import ru.practicum.explorewithme.model.category.CategoryDto;
import ru.practicum.explorewithme.service.category.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/categories")
@Validated
public class CategoriesController {
    CategoryService service;

    @Autowired
    public CategoriesController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam @PositiveOrZero Integer from,
                                           @RequestParam @Positive Integer size) {
        return service.getCategories(new CustomPageRequest(from, size, Sort.unsorted()));
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategory(@PathVariable Long catId) {
        return service.getCategoryById(catId);
    }
}
