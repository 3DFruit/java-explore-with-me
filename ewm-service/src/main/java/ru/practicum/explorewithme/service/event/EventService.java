package ru.practicum.explorewithme.service.event;

import org.springframework.data.domain.Pageable;
import ru.practicum.explorewithme.model.EventSortOption;
import ru.practicum.explorewithme.model.event.*;


import java.util.List;

public interface EventService {
    List<EventFullDto> getEvents(List<Long> users,
                                 List<String> states,
                                 List<Long> categories,
                                 String rangeStart,
                                 String rangeEnd,
                                 Pageable pageable);
    EventFullDto updateEvent(Long eventId, UpdateEventAdminRequest admin);
    List<EventShortDto> getEventsOfUser(Long userId, Pageable pageable);
    EventFullDto createEvent(Long userId, NewEventDto eventDto);
    EventFullDto getEventById(Long userId, Long eventId);
    EventFullDto patchEvent(Long userId, Long eventId,UpdateEventUserRequest userRequest);
    List<EventShortDto> getPublishedEvents(String text,
                                           List<Long> categories,
                                           Boolean paid,
                                           String rangeStart,
                                           String rangeEnd,
                                           Boolean onlyAvailable,
                                           EventSortOption sort,
                                           Pageable pageable);
    EventFullDto getPublishedEventById(Long id);
}
