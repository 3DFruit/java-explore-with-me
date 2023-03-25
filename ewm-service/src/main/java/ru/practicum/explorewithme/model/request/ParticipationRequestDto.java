package ru.practicum.explorewithme.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParticipationRequestDto {
    Long id;
    LocalDateTime created;
    Long event;
    Long requester;
    RequestState status;
}
