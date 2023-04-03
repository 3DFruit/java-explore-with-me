package ru.practicum.explorewithme.service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.model.category.Category;
import ru.practicum.explorewithme.model.category.CategoryDto;
import ru.practicum.explorewithme.model.category.CategoryMapper;
import ru.practicum.explorewithme.model.category.NewCategoryDto;
import ru.practicum.explorewithme.model.exception.ObjectNotFoundException;
import ru.practicum.explorewithme.storage.CategoryStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryStorage categoryStorage;
    private final CategoryMapper mapper;

    @Override
    @Transactional
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        return mapper.toCategoryDto(categoryStorage.save(mapper.toCategory(newCategoryDto)));
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        categoryStorage.deleteById(id);
    }

    @Override
    @Transactional
    public CategoryDto patchCategory(Long id, CategoryDto dto) {
        Category category = categoryStorage.findById(id)
                .orElseThrow(() -> {
                    throw new ObjectNotFoundException("Не найден объект типа " + Category.class + " c id " + id);
                });
        if (dto.getName() != null) {
            category.setName(dto.getName());
        }
        return mapper.toCategoryDto(categoryStorage.save(category));
    }

    @Override
    public List<CategoryDto> getCategories(Pageable pageable) {
        return categoryStorage.findAll(pageable).stream()
                .map(mapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryStorage.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Не найдена категория с id " + id));
        return mapper.toCategoryDto(category);
    }
}
