package ru.practicum.explorewithme.storage.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.model.category.Category;

@Repository
public interface CategoryStorage extends JpaRepository<Category, Long> {
}
