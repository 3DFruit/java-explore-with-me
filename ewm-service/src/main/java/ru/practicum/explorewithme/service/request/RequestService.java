package ru.practicum.explorewithme.service.request;

import ru.practicum.explorewithme.model.event.Event;
import ru.practicum.explorewithme.model.request.EventRequestStatusUpdateRequest;
import ru.practicum.explorewithme.model.request.EventRequestStatusUpdateResult;
import ru.practicum.explorewithme.model.request.ParticipationRequestDto;
import ru.practicum.explorewithme.model.request.Request;

import java.util.Collection;
import java.util.List;

public interface RequestService {
    List<ParticipationRequestDto> getRequestsOfUser(Long userId);

    ParticipationRequestDto createRequest(Long userId, Long eventId);

    ParticipationRequestDto cancelRequest(Long userId, Long requestId);

    List<ParticipationRequestDto> getRequestsOfEvent(Long userId, Long eventId);

    EventRequestStatusUpdateResult patchRequestsOfEvent(Long userId, Long eventId,
                                                        EventRequestStatusUpdateRequest request);

    List<Request> getApprovedRequests(Collection<Event> events);
}
