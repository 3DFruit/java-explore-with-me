package ru.practicum.explorewithme.model.event;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateEventAdminRequest {
    @NotEmpty
    @Size(min = 20, max = 2000)
    String annotation;
    Long category;
    @NotEmpty
    @Size(min = 20, max = 7000)
    String description;
    @EventDateConstraint
    LocalDateTime eventDate;
    LocationDto location;
    Boolean paid;
    @PositiveOrZero Integer participantLimit;
    Boolean requestModeration;
    AdminStateAction stateAction;
    @NotEmpty
    @Size(min = 3, max = 120)
    String title;
}
