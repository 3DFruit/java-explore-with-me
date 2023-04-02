package ru.practicum.explorewithme.service.event;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.client.StatisticsClient;
import ru.practicum.explorewithme.model.EventSortOption;
import ru.practicum.explorewithme.model.ViewStats;
import ru.practicum.explorewithme.model.category.Category;
import ru.practicum.explorewithme.model.event.*;
import ru.practicum.explorewithme.model.exception.AdminUpdateStatusException;
import ru.practicum.explorewithme.model.exception.ObjectNotFoundException;
import ru.practicum.explorewithme.model.exception.UserUpdateStatusException;
import ru.practicum.explorewithme.model.request.QRequest;
import ru.practicum.explorewithme.model.request.Request;
import ru.practicum.explorewithme.model.user.User;
import ru.practicum.explorewithme.storage.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private final EventStorage eventStorage;
    private final UserStorage userStorage;
    private final CategoryStorage categoryStorage;
    private final LocationStorage locationStorage;
    private final StatisticsClient client;
    private final EventMapper mapper;
    private final RequestStorage requestStorage;

    @Override
    public List<EventFullDto> getEvents(List<Long> users,
                                        List<String> states,
                                        List<Long> categories,
                                        LocalDateTime rangeStart,
                                        LocalDateTime rangeEnd,
                                        Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (users != null && !users.isEmpty()) {
            booleanBuilder.and(QEvent.event.initiator.id.in(users));
        }
        if (states != null && !states.isEmpty()) {
            List<EventState> eventStates = states.stream().map(state -> EventState.from(state).orElseThrow(() -> new RuntimeException("Не удалось найти статус " + state)))
                    .collect(Collectors.toList());
            booleanBuilder.and(QEvent.event.state.in(eventStates));
        }
        if (categories != null && !categories.isEmpty()) {
            booleanBuilder.and(QEvent.event.category.id.in(categories));
        }
        if (rangeStart != null && rangeEnd != null) {
            booleanBuilder.and(QEvent.event.eventDate.between(rangeStart, rangeEnd));
        }
        List<Event> events = eventStorage.findAll(booleanBuilder, pageable).getContent();
        return events.stream().map(mapper::toEventFullDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(Long eventId, UpdateEventAdminRequest adminRequest) {
        Event event = eventStorage.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("Не найдено событие с id " + eventId));
        if (adminRequest.getAnnotation() != null) {
            event.setAnnotation(adminRequest.getAnnotation());
        }
        if (adminRequest.getCategory() != null) {
            Category category = categoryStorage.findById(adminRequest.getCategory())
                    .orElseThrow(() -> new ObjectNotFoundException("Не найдена категория с id " + eventId));
            event.setCategory(category);
        }
        if (adminRequest.getDescription() != null) {
            event.setDescription(adminRequest.getDescription());
        }
        if (adminRequest.getEventDate() != null) {
            event.setEventDate(adminRequest.getEventDate());
        }
        if (adminRequest.getLocation() != null) {
            LocationDto locationDto = adminRequest.getLocation();
            Location location = locationStorage.findByLatAndLon(locationDto.getLat(), locationDto.getLon()).orElse(
                    locationStorage.save(new Location(locationDto.getLat(), locationDto.getLon()))
            );
            event.setLocation(location);
        }
        if (adminRequest.getPaid() != null) {
            event.setPaid(adminRequest.getPaid());
        }
        if (adminRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(adminRequest.getParticipantLimit());
        }
        if (adminRequest.getRequestModeration() != null) {
            event.setRequestModeration(adminRequest.getRequestModeration());
        }
        if (adminRequest.getTitle() != null) {
            event.setTitle(adminRequest.getTitle());
        }
        if (adminRequest.getStateAction() != null) {
            switch (adminRequest.getStateAction()) {
                case REJECT_EVENT:
                    if (event.getState() == EventState.PUBLISHED) {
                        throw new AdminUpdateStatusException("Нельзя отменить опубликованное событие");
                    }
                    event.setState(EventState.CANCELED);
                    break;
                case PUBLISH_EVENT:
                    if (event.getState() != EventState.PENDING) {
                        throw new AdminUpdateStatusException("Нельзя опубликовать событие, которое не в  статусе PENDING");
                    }
                    event.setState(EventState.PUBLISHED);
            }
        }
        eventStorage.save(event);
        return mapper.toEventFullDto(setConfirmedRequestAndViews(event));
    }

    @Override
    public List<EventShortDto> getEventsOfUser(Long userId, Pageable pageable) {
        User user = userStorage.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Не найден пользователь с id " + userId));
        List<Event> events = setConfirmedRequestsAndViews(eventStorage.findByInitiator(user, pageable).getContent());
        return events.stream().map(mapper::toEventShortDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto createEvent(Long userId, NewEventDto eventDto) {
        User user = userStorage.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Не найден пользователь с id " + userId));
        Event event = this.toEvent(eventDto);
        event.setInitiator(user);
        event.setCreatedOn(LocalDateTime.now());
        event.setState(EventState.PENDING);
        return mapper.toEventFullDto(eventStorage.save(event));
    }

    private Event toEvent(NewEventDto dto) {
        Event event = new Event();
        event.setAnnotation(dto.getAnnotation());
        event.setDescription(dto.getDescription());
        event.setEventDate(dto.getEventDate());
        event.setPaid(dto.getPaid());
        event.setParticipantLimit(dto.getParticipantLimit());
        event.setRequestModeration(dto.getRequestModeration());
        event.setTitle(dto.getTitle());
        event.setCategory(categoryStorage.findById(dto.getCategory())
                .orElseThrow(() -> new ObjectNotFoundException("Не найдена категория с id " + dto.getCategory())));
        LocationDto locationDto = dto.getLocation();
        Location location = locationStorage.findByLatAndLon(locationDto.getLat(), locationDto.getLon()).orElse(
                locationStorage.save(new Location(locationDto.getLat(), locationDto.getLon()))
        );
        event.setLocation(location);
        return event;
    }

    @Override
    public EventFullDto getEventById(Long userId, Long eventId) {
        User user = userStorage.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Не найден пользователь с id " + userId));
        Event event = eventStorage.findByIdAndInitiator(eventId, user)
                .orElseThrow(() -> new ObjectNotFoundException("Не найдено событие с id " + eventId));
        return mapper.toEventFullDto(setConfirmedRequestAndViews(event));
    }

    @Override
    @Transactional
    public EventFullDto patchEvent(Long userId, Long eventId, UpdateEventUserRequest userRequest) {
        User user = userStorage.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Не найден пользователь с id " + userId));
        Event event = eventStorage.findByIdAndInitiator(eventId, user)
                .orElseThrow(() -> new ObjectNotFoundException("Не найдено событие с id " + eventId));
        if (event.getState() != EventState.CANCELED && event.getState() != EventState.PENDING) {
            throw new UserUpdateStatusException("Изменить статус можно только из статусов PENDING и CANCELED");
        }
        if (userRequest.getAnnotation() != null) {
            event.setAnnotation(userRequest.getAnnotation());
        }
        if (userRequest.getCategory() != null) {
            Category category = categoryStorage.findById(userRequest.getCategory())
                    .orElseThrow(() -> new ObjectNotFoundException("Не найдена категория с id " + eventId));
            event.setCategory(category);
        }
        if (userRequest.getDescription() != null) {
            event.setDescription(userRequest.getDescription());
        }
        if (userRequest.getEventDate() != null) {
            event.setEventDate(userRequest.getEventDate());
        }
        if (userRequest.getLocation() != null) {
            LocationDto locationDto = userRequest.getLocation();
            Location location = locationStorage.findByLatAndLon(locationDto.getLat(), locationDto.getLon()).orElse(
                    locationStorage.save(new Location(locationDto.getLat(), locationDto.getLon()))
            );
            event.setLocation(location);
        }
        if (userRequest.getPaid() != null) {
            event.setPaid(userRequest.getPaid());
        }
        if (userRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(userRequest.getParticipantLimit());
        }
        if (userRequest.getRequestModeration() != null) {
            event.setRequestModeration(userRequest.getRequestModeration());
        }
        if (userRequest.getTitle() != null) {
            event.setTitle(userRequest.getTitle());
        }

        if (userRequest.getStateAction() != null) {
            switch (userRequest.getStateAction()) {
                case CANCEL_REVIEW:
                    event.setState(EventState.CANCELED);
                    break;
                case SEND_TO_REVIEW:
                    event.setState(EventState.PENDING);
            }
        }
        return mapper.toEventFullDto(setConfirmedRequestAndViews(eventStorage.save(event)));
    }

    @Override
    public List<EventShortDto> getPublishedEvents(String text,
                                                  List<Long> categories,
                                                  Boolean paid,
                                                  LocalDateTime rangeStart,
                                                  LocalDateTime rangeEnd,
                                                  Boolean onlyAvailable,
                                                  Integer from,
                                                  Integer size,
                                                  EventSortOption sortOption) {
        BooleanBuilder booleanBuilder = new BooleanBuilder(QEvent.event.state.eq(EventState.PUBLISHED));
        if (text != null && !text.isBlank()) {
            BooleanExpression byTextInAnnotation = QEvent.event.annotation.likeIgnoreCase("%" + text + "%");
            BooleanExpression byTextInDescription = QEvent.event.description.likeIgnoreCase("%" + text + "%");
            booleanBuilder.and(byTextInAnnotation.or(byTextInDescription));
        }
        if (categories != null && categories.size() != 0) {
            booleanBuilder.and(QEvent.event.category.id.in(categories));
        }
        if (paid != null) {
            booleanBuilder.and(QEvent.event.paid.eq(paid));
        }
        if (rangeStart != null && rangeEnd != null) {
            booleanBuilder.and(QEvent.event.eventDate.between(rangeStart, rangeEnd));
        } else {
            booleanBuilder.and(QEvent.event.eventDate.after(LocalDateTime.now()));
        }
        if (onlyAvailable != null && onlyAvailable) {
            BooleanExpression withoutLimit = QEvent.event.participantLimit.eq(0);
            BooleanExpression withLimitAvailable = QEvent.event.participantLimit.gt(
                    JPAExpressions.select(QRequest.request.count())
                            .from(QRequest.request)
                            .where(QRequest.request.event.eq(QEvent.event))
            );
            booleanBuilder.and(withoutLimit.or(withLimitAvailable));
        }
        List<Event> events = new ArrayList<>();
        eventStorage.findAll(booleanBuilder).forEach(events::add);
        events = setConfirmedRequestsAndViews(events);
        switch (sortOption) {
            case EVENT_DATE:
                events = events.stream().sorted(Comparator.comparing(Event::getEventDate)).skip(from).limit(size).collect(Collectors.toList());
                break;
            case VIEWS:
                events = events.stream().sorted(Comparator.comparing(Event::getViews)).skip(from).limit(size).collect(Collectors.toList());
                break;
            default:
                events = events.stream().skip(from).limit(size).collect(Collectors.toList());
        }
        return events.stream().map(mapper::toEventShortDto).collect(Collectors.toList());
    }

    @Override
    public EventFullDto getPublishedEventById(Long id) {
        Event event = eventStorage.findByIdAndState(id, EventState.PUBLISHED)
                .orElseThrow(() -> new ObjectNotFoundException("Не найдено событие с id " + id));
        return mapper.toEventFullDto(setConfirmedRequestAndViews(event));
    }

    @Override
    public Event getEventById(Long eventId) {
        Event event = eventStorage.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("Не найдено событие с id " + eventId));
        return setConfirmedRequestAndViews(event);
    }

    private Event setConfirmedRequestAndViews(Event event) {
        return setConfirmedRequestsAndViews(Collections.singletonList(event)).stream().findFirst().orElseThrow(() -> {
            throw new RuntimeException("Ошибка получения данных о просмотрах и/или запросах");
        });
    }

    private List<Event> setConfirmedRequestsAndViews(Collection<Event> events) {
        return setViews(setConfirmedRequests(events));
    }

    private List<Event> setConfirmedRequests(Collection<Event> events) {
        if (events == null || events.isEmpty()) {
            return new ArrayList<>();
        }
        List<Request> requests = requestStorage.findByEventIdIn(events.stream()
                .map(Event::getId).collect(Collectors.toList()));
        return events.stream()
                .peek(event -> event.setConfirmedRequests(
                        requests.stream()
                                .filter(request -> request.getEvent().equals(event))
                                .count()))
                .collect(Collectors.toList());
    }

    private List<Event> setViews(Collection<Event> events) {
        if (events == null || events.isEmpty()) {
            return new ArrayList<>();
        }
        LocalDateTime from = events.stream()
                .min(Comparator.comparing(Event::getCreatedOn))
                .orElseThrow(() -> new RuntimeException("Событие без времени создания!"))
                .getCreatedOn();
        Map<Long, Long> views = getViews(events.stream().map(Event::getId).collect(Collectors.toList()), from);
        return events.stream()
                .peek(event -> event.setViews(views.getOrDefault(event.getId(), 0L)))
                .collect(Collectors.toList());
    }

    private Map<Long, Long> getViews(List<Long> ids, LocalDateTime from) {
        if (ids == null || ids.isEmpty()) {
            return new HashMap<>();
        }
        List<ViewStats> stats = client.getStats(from,
                        LocalDateTime.now(),
                        ids.stream().map(id -> "/events/" + id).collect(Collectors.toList()),
                        false)
                .block();
        if (stats == null || stats.isEmpty()) {
            return new HashMap<>();
        }
        return stats.stream().collect(Collectors.toMap(
                stat -> Long.parseLong(stat.getUri().replace("/events/", "")),
                ViewStats::getHits
        ));
    }
}
