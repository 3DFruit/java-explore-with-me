package ru.practicum.explorewithme.service.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.model.event.Event;
import ru.practicum.explorewithme.model.event.EventState;
import ru.practicum.explorewithme.model.exception.*;
import ru.practicum.explorewithme.model.request.*;
import ru.practicum.explorewithme.model.user.User;
import ru.practicum.explorewithme.service.event.EventService;
import ru.practicum.explorewithme.storage.RequestStorage;
import ru.practicum.explorewithme.storage.UserStorage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {
    private final RequestStorage requestStorage;
    private final UserStorage userStorage;
    private final EventService eventService;
    private final RequestMapper mapper;

    @Override
    public List<ParticipationRequestDto> getRequestsOfUser(Long userId) {
        User user = userStorage.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Не найден пользователь с id " + userId));
        return requestStorage.findByRequester(user).stream()
                .map(mapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        User user = userStorage.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Не найден пользователь с id " + userId));
        Event event = eventService.getEventById(eventId);
        if (event.getState() != EventState.PUBLISHED) {
            throw new RequestCreationException("Нельзя создать запрос на неопубликованное событие");
        }
        if (event.getInitiator().equals(user)) {
            throw new RequestCreationException("Инициатор не может оставить запрос на свое событие");
        }
        Request request = new Request();
        request.setRequester(user);
        request.setEvent(event);
        request.setStatus(event.getRequestModeration() ? RequestState.PENDING : RequestState.CONFIRMED);
        request.setCreated(LocalDateTime.now());
        if (event.getParticipantLimit() <= event.getConfirmedRequests()) {
            throw new RequestCreationException("Не осталось свободных мест в данном событии");
        }
        return mapper.toParticipationRequestDto(requestStorage.save(request));
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        User user = userStorage.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Не найден пользователь с id " + userId));
        Request request = requestStorage.findById(requestId)
                .orElseThrow(() -> new ObjectNotFoundException("Не найден запрос с id " + requestId));
        if (!request.getRequester().equals(user)) {
            throw new PermissionException("Запрос создан другим пользователем");
        }
        request.setStatus(RequestState.CANCELED);
        return mapper.toParticipationRequestDto(requestStorage.save(request));
    }

    @Override
    public List<ParticipationRequestDto> getRequestsOfEvent(Long userId, Long eventId) {
        User user = userStorage.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Не найден пользователь с id " + userId));
        Event event = eventService.getEventById(eventId);
        if (!event.getInitiator().equals(user)) {
            throw new PermissionException("Событие создано другим пользователем");
        }
        return requestStorage.findByEvent(event).stream()
                .map(mapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult patchRequestsOfEvent(Long userId, Long eventId, EventRequestStatusUpdateRequest request) {
        User user = userStorage.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Не найден пользователь с id " + userId));
        Event event = eventService.getEventById(eventId);
        if (!event.getInitiator().equals(user)) {
            throw new PermissionException("Событие создано другим пользователем");
        }
        if (request.getStatus() == RequestUserState.CONFIRMED && event.getParticipantLimit() <= event.getConfirmedRequests()) {
            throw new FullEventException("Не осталось свободных мест в данном событии");
        }
        EventRequestStatusUpdateResult updateResult = new EventRequestStatusUpdateResult();
        for (Long requestId : request.getRequestIds()) {
            Request req = requestStorage.findById(requestId)
                    .orElseThrow(() -> new ObjectNotFoundException("Не найден запрос с id " + requestId));
            if (req.getStatus() != RequestState.PENDING) {
                throw new RequestStatusException("Нельзя изменить статус у заявки не в статусе ожидания");
            }
            switch (request.getStatus()) {
                case REJECTED:
                    req.setStatus(RequestState.REJECTED);
                    updateResult.addRejectedRequest(mapper.toParticipationRequestDto(req));
                    break;
                case CONFIRMED:
                    if (event.getParticipantLimit() == 0
                            || event.getParticipantLimit() > event.getConfirmedRequests()) {
                        req.setStatus(RequestState.CONFIRMED);
                        updateResult.addConfirmedRequest(mapper.toParticipationRequestDto(req));
                    } else {
                        req.setStatus(RequestState.REJECTED);
                        updateResult.addRejectedRequest(mapper.toParticipationRequestDto(req));
                    }
            }
            requestStorage.save(req);
        }
        return updateResult;
    }
}
