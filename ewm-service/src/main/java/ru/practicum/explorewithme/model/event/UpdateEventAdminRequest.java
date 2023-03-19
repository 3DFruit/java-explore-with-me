package ru.practicum.explorewithme.model.event;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateEventAdminRequest {
    @NotEmpty
    @Size(min = 20, max = 2000)
    String annotation;
    Long category;
    @NotEmpty
    @Size(min = 20, max = 7000)
    String description;
    @Future
    LocalDateTime eventDate;
    Location location;
    Boolean paid;
    @Positive Integer participantLimit;
    Boolean requestModeration;
    AdminStateAction stateAction;
    @NotEmpty
    @Size(min = 3, max = 120)
    String title;
}
