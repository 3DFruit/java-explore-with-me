package ru.practicum.explorewithme.service.compilation;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.model.compilation.CompilationDto;
import ru.practicum.explorewithme.model.compilation.NewCompilationDto;
import ru.practicum.explorewithme.model.compilation.UpdateCompilationRequest;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {
    @Override
    public CompilationDto createCompilation(NewCompilationDto compilationDto) {
        return null;
    }

    @Override
    public void deleteCompilation(Long compId) {

    }

    @Override
    public CompilationDto patchCompilation(Long compId, UpdateCompilationRequest request) {
        return null;
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Pageable pageable) {
        return null;
    }

    @Override
    public CompilationDto getCompilationById(Integer compId) {
        return null;
    }
}
