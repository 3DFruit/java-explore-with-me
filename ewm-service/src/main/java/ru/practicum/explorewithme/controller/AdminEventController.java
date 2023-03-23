package ru.practicum.explorewithme.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.model.event.EventFullDto;
import ru.practicum.explorewithme.model.event.UpdateEventAdminRequest;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/events")
@Validated
public class AdminEventController {
    @GetMapping
    public List<EventFullDto> getEvents(@RequestParam List<Long> users,
                                        @RequestParam List<String> states,
                                        @RequestParam List<Long> categories,
                                        @RequestParam String rangeStart,
                                        @RequestParam String rangeEnd,
                                        @RequestParam @PositiveOrZero Integer from,
                                        @RequestParam @Positive Integer size) {
        return null;
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(@PathVariable Long eventId, @RequestBody UpdateEventAdminRequest admin) {
        return null;
    }
}
