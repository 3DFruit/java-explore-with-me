package ru.practicum.explorewithme.model.compilation;

import lombok.*;
import ru.practicum.explorewithme.model.event.EventShortDto;

import java.util.List;

@Data
public class UpdateCompilationRequest {
    private List<EventShortDto> events;
    private Boolean pinned;
    private String title;
}
