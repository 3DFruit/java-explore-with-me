package ru.practicum.explorewithme.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.model.event.EventFullDto;
import ru.practicum.explorewithme.model.event.EventShortDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/events")
@Validated
public class PublicEventController {
    @GetMapping
    public List<EventShortDto> getPublishedEvents(@RequestParam String text,
                                                  @RequestParam List<Long> categories,
                                                  @RequestParam Boolean paid,
                                                  @RequestParam String rangeStart,
                                                  @RequestParam String rangeEnd,
                                                  @RequestParam Boolean onlyAvailable,
                                                  @RequestParam String sort,
                                                  @RequestParam @PositiveOrZero Integer from,
                                                  @RequestParam @Positive Integer size) {
        return null;
    }

    @GetMapping("/{id}")
    public EventFullDto getPublishedEventById(@PathVariable Long id) {
        return null;
    }
}
