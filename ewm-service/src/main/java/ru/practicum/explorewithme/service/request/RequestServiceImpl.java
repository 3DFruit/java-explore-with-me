package ru.practicum.explorewithme.service.request;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.model.request.EventRequestStatusUpdateRequest;
import ru.practicum.explorewithme.model.request.EventRequestStatusUpdateResult;
import ru.practicum.explorewithme.model.request.ParticipationRequestDto;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {
    @Override
    public List<ParticipationRequestDto> getRequestsOfUser(Long userId) {
        return null;
    }

    @Override
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        return null;
    }

    @Override
    public List<ParticipationRequestDto> getRequestsOfEvent(Long userId, Long eventId) {
        return null;
    }

    @Override
    public EventRequestStatusUpdateResult patchRequestsOfEvent(Long userId, Long eventId, EventRequestStatusUpdateRequest request) {
        return null;
    }
}
