package ru.practicum.explorewithme.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.model.CustomPageRequest;
import ru.practicum.explorewithme.model.event.EventFullDto;
import ru.practicum.explorewithme.model.event.UpdateEventAdminRequest;
import ru.practicum.explorewithme.service.event.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/events")
@Validated
public class AdminEventController {
    EventService eventService;

    @Autowired
    public AdminEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventFullDto> getEvents(@RequestParam List<Long> users,
                                        @RequestParam List<String> states,
                                        @RequestParam List<Long> categories,
                                        @RequestParam String rangeStart,
                                        @RequestParam String rangeEnd,
                                        @RequestParam @PositiveOrZero Integer from,
                                        @RequestParam @Positive Integer size) {
        log.trace("Запрос событий от пользователей {} в состояниях {} c категориями {} за период {}-{}",
                users, states, categories, rangeStart, rangeEnd);
        return eventService.getEvents(users, states, categories, rangeStart, rangeEnd,
                new CustomPageRequest(from, size));
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(@PathVariable Long eventId, @RequestBody UpdateEventAdminRequest adminRequest) {
        log.trace("Обновление события с id - {}", eventId);
        return eventService.updateEvent(eventId, adminRequest);
    }
}
