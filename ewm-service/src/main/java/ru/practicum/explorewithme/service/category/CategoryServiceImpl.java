package ru.practicum.explorewithme.service.category;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.model.category.Category;
import ru.practicum.explorewithme.model.category.CategoryDto;
import ru.practicum.explorewithme.model.category.NewCategoryDto;
import ru.practicum.explorewithme.model.exception.ObjectNotFoundException;
import ru.practicum.explorewithme.storage.CategoryStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryStorage categoryStorage;
    private final ObjectMapper mapper;

    @Autowired
    public CategoryServiceImpl(CategoryStorage categoryStorage) {
        this.categoryStorage = categoryStorage;

        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Override
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        return mapper.convertValue(
                categoryStorage.save(mapper.convertValue(newCategoryDto, Category.class)), CategoryDto.class
        );
    }

    @Override
    public void deleteCategory(Long id) {
        categoryStorage.deleteById(id);
    }

    @Override
    public CategoryDto patchCategory(Long id, CategoryDto dto) {
        Category category = categoryStorage.findById(id)
                .orElseThrow(() -> {
                    throw new ObjectNotFoundException("Не найден объект типа " + Category.class + " c id " + id);
                });
        if (dto.getName() != null) {
            category.setName(dto.getName());
        }
        return mapper.convertValue(
                categoryStorage.save(category), CategoryDto.class
        );
    }

    @Override
    public List<CategoryDto> getCategories(Pageable pageable) {
        return categoryStorage.findAll(pageable).stream()
                .map(category -> mapper.convertValue(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        return mapper.convertValue(categoryStorage.findById(id), CategoryDto.class);
    }
}
