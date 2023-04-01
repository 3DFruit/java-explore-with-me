package ru.practicum.explorewithme.model.request;

import lombok.Data;

import java.util.List;

@Data
public class EventRequestStatusUpdateResult {
    List<ParticipationRequestDto> confirmedRequests;
    List<ParticipationRequestDto> rejectedRequests;

    public void addConfirmedRequest(ParticipationRequestDto request) {
        confirmedRequests.add(request);
    }

    public void addRejectedRequest(ParticipationRequestDto request) {
        rejectedRequests.add(request);
    }
}
