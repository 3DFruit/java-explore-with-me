package ru.practicum.explorewithme.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.client.StatisticsClient;
import ru.practicum.explorewithme.model.CustomPageRequest;
import ru.practicum.explorewithme.model.EventSortOption;
import ru.practicum.explorewithme.model.event.EventFullDto;
import ru.practicum.explorewithme.model.event.EventShortDto;
import ru.practicum.explorewithme.service.event.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/events")
@Validated
public class PublicEventController {
    EventService eventService;
    StatisticsClient client;

    @Autowired
    public PublicEventController(EventService eventService, StatisticsClient client) {
        this.eventService = eventService;
        this.client = client;
    }

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
        log.trace("Поиск опубликованного события: текст = '{}', категории {}, платные {} за период {}-{} только доступные {}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable);
        EventSortOption sortOption = EventSortOption.from(sort).orElseThrow();
        return eventService.getPublishedEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
                sortOption, new CustomPageRequest(from, size));
    }

    @GetMapping("/{id}")
    public EventFullDto getPublishedEventById(@PathVariable Long id) {
        log.trace("Запрос опубликованного события {}", id);
        return eventService.getPublishedEventById(id);
    }
}
