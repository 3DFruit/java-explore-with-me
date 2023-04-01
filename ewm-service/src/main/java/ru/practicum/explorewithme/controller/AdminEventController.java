package ru.practicum.explorewithme.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.model.CustomPageRequest;
import ru.practicum.explorewithme.model.event.EventFullDto;
import ru.practicum.explorewithme.model.event.UpdateEventAdminRequest;
import ru.practicum.explorewithme.service.event.EventService;
import ru.practicum.explorewithme.utils.CommonUtils;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
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
    public List<EventFullDto> getEvents(@RequestParam(required = false) List<Long> users,
                                        @RequestParam(required = false) List<String> states,
                                        @RequestParam(required = false) List<Long> categories,
                                        @RequestParam(required = false) String rangeStart,
                                        @RequestParam(required = false) String rangeEnd,
                                        @RequestParam(defaultValue = CommonUtils.PAGINATION_DEFAULT_FROM) @PositiveOrZero Integer from,
                                        @RequestParam(defaultValue = CommonUtils.PAGINATION_DEFAULT_SIZE) @Positive Integer size) {
        log.trace("Запрос событий от пользователей {} в состояниях {} c категориями {} за период {}-{}",
                users, states, categories, rangeStart, rangeEnd);
        return eventService.getEvents(users, states, categories,
                rangeStart != null ? LocalDateTime.parse(rangeStart, CommonUtils.DATE_TIME_FORMATTER) : null,
                rangeEnd != null ? LocalDateTime.parse(rangeEnd, CommonUtils.DATE_TIME_FORMATTER) : null, new CustomPageRequest(from, size));
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(@PathVariable Long eventId, @RequestBody @Valid UpdateEventAdminRequest adminRequest) {
        log.trace("Обновление события с id - {}", eventId);
        return eventService.updateEvent(eventId, adminRequest);
    }
}
