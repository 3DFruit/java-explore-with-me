package ru.practicum.explorewithme.model.compilation;

import lombok.Data;
import ru.practicum.explorewithme.model.event.EventShortDto;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class NewCompilationDto {
    private List<EventShortDto> events;
    private Boolean pinned;
    @NotNull
    private String title;
}
