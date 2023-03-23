package ru.practicum.explorewithme.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.model.compilation.Compilation;

@Repository
public interface CompilationStorage extends JpaRepository<Compilation, Long> {
}
