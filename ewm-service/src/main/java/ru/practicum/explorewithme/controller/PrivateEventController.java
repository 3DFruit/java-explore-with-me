package ru.practicum.explorewithme.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.model.event.EventFullDto;
import ru.practicum.explorewithme.model.event.EventShortDto;
import ru.practicum.explorewithme.model.event.NewEventDto;
import ru.practicum.explorewithme.model.event.UpdateEventUserRequest;
import ru.practicum.explorewithme.model.request.EventRequestStatusUpdateRequest;
import ru.practicum.explorewithme.model.request.EventRequestStatusUpdateResult;
import ru.practicum.explorewithme.model.request.ParticipationRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/events")
@Validated
public class PrivateEventController {
    @GetMapping
    public List<EventShortDto> getEventsOfUser(@PathVariable Long userId,
                                               @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                               @RequestParam(defaultValue = "10") @Positive Integer size) {
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@PathVariable Long userId, @RequestBody @Valid NewEventDto eventDto) {
        return null;
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventById(@PathVariable Long userId, @PathVariable Long eventId) {
        return null;
    }

    @PatchMapping("/{eventId}")
    public EventFullDto patchEvent(@PathVariable Long userId, @PathVariable Long eventId,
                                   @RequestBody @Valid UpdateEventUserRequest userRequest) {
        return null;
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestsOfEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        return null;
    }

    @GetMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult patchRequestsOfEvent(@PathVariable Long userId, @PathVariable Long eventId,
                                                               @RequestBody EventRequestStatusUpdateRequest request) {
        return null;
    }
}
