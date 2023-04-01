package ru.practicum.explorewithme.service.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.model.compilation.*;
import ru.practicum.explorewithme.model.event.Event;
import ru.practicum.explorewithme.model.event.EventMapper;
import ru.practicum.explorewithme.model.event.EventShortDto;
import ru.practicum.explorewithme.model.exception.ObjectNotFoundException;
import ru.practicum.explorewithme.storage.CompilationStorage;
import ru.practicum.explorewithme.storage.EventStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {
    private final CompilationStorage compilationStorage;
    private final CompilationMapper mapper;
    private final EventStorage eventStorage;
    private final EventMapper eventMapper;

    @Override
    @Transactional
    public CompilationDto createCompilation(NewCompilationDto compilationDto) {
        List<Event> events = eventStorage.findAllById(compilationDto.getEvents().stream()
                .map(EventShortDto::getId).collect(Collectors.toList()));
        List<EventShortDto> shortDtos = events.stream()
                .map(eventMapper::toEventShortDto).collect(Collectors.toList());
        return mapper.toCompilationDto(compilationStorage.save(mapper.newDtoToCompilation(compilationDto, events)), shortDtos);
    }

    @Override
    @Transactional
    public void deleteCompilation(Long compId) {
        compilationStorage.deleteById(compId);
    }

    @Override
    @Transactional
    public CompilationDto patchCompilation(Long compId, UpdateCompilationRequest request) {
        Compilation compilation = compilationStorage.findById(compId)
                .orElseThrow(() -> new ObjectNotFoundException("Не найдена подборка с id " + compId));
        if (request.getPinned() != null) {
            compilation.setPinned(request.getPinned());
        }
        if (request.getTitle() != null) {
            compilation.setTitle(request.getTitle());
        }
        if (request.getEvents() != null && !request.getEvents().isEmpty()) {
            compilation.setEvents(eventStorage.findAllById(request.getEvents().stream()
                    .map(EventShortDto::getId).collect(Collectors.toList())));
        }
        List<EventShortDto> shortDtos = compilation.getEvents().stream()
                .map(eventMapper::toEventShortDto).collect(Collectors.toList());
        return mapper.toCompilationDto(compilationStorage.save(compilation), shortDtos);
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Pageable pageable) {
        return compilationStorage.findByPinned(pinned, pageable).stream()
                .map(comp -> mapper.toCompilationDto(comp, comp.getEvents()
                        .stream().map(eventMapper::toEventShortDto).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilationById(Long compId) {
        Compilation compilation = compilationStorage.findById(compId)
                .orElseThrow(() -> new ObjectNotFoundException("Не найдена подборка с id " + compId));
        return mapper.toCompilationDto(compilation, compilation.getEvents()
                .stream().map(eventMapper::toEventShortDto).collect(Collectors.toList()));
    }
}
