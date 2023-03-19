package ru.practicum.explorewithme.service.category;

import ru.practicum.explorewithme.model.category.CategoryDto;
import ru.practicum.explorewithme.model.category.NewCategoryDto;

public interface CategoryService {
    CategoryDto createCategory(NewCategoryDto newCategoryDto);
    void deleteCategory(Long id);
    CategoryDto patchCategory(Long id, CategoryDto dto);
}
