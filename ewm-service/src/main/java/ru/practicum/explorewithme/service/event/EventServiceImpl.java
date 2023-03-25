package ru.practicum.explorewithme.service.event;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.model.EventSortOption;
import ru.practicum.explorewithme.model.event.*;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    @Override
    public List<EventFullDto> getEvents(List<Long> users, List<String> states, List<Long> categories, String rangeStart, String rangeEnd, Pageable pageable) {
        return null;
    }

    @Override
    public EventFullDto updateEvent(Long eventId, UpdateEventAdminRequest admin) {
        return null;
    }

    @Override
    public List<EventShortDto> getEventsOfUser(Long userId, Pageable pageable) {
        return null;
    }

    @Override
    public EventFullDto createEvent(Long userId, NewEventDto eventDto) {
        return null;
    }

    @Override
    public EventFullDto getEventById(Long userId, Long eventId) {
        return null;
    }

    @Override
    public EventFullDto patchEvent(Long userId, Long eventId, UpdateEventUserRequest userRequest) {
        return null;
    }

    @Override
    public List<EventShortDto> getPublishedEvents(String text,
                                                  List<Long> categories,
                                                  Boolean paid,
                                                  String rangeStart,
                                                  String rangeEnd,
                                                  Boolean onlyAvailable,
                                                  EventSortOption sort,
                                                  Pageable pageable) {
        return null;
    }

    @Override
    public EventFullDto getPublishedEventById(Long id) {
        return null;
    }
}
