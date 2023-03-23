package ru.practicum.explorewithme.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.model.request.ParticipationRequestDto;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/requests")
@Validated
public class PrivateRequestController {
    @GetMapping
    public List<ParticipationRequestDto> getRequestsOfUser(@PathVariable Long userId) {
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto postRequest(@PathVariable Long userId, @RequestParam Long eventId) {
        return null;
    }

    @PatchMapping("/{requestId}")
    public ParticipationRequestDto patchRequest(@PathVariable Long userId, @PathVariable Long requestId) {
        return null;
    }
}
