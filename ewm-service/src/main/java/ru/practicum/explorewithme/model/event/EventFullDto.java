package ru.practicum.explorewithme.model.event;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explorewithme.model.category.CategoryDto;
import ru.practicum.explorewithme.model.user.UserShortDto;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventFullDto {
    @NotBlank String annotation;
    @NotNull CategoryDto category;
    Long confirmedRequests;
    LocalDateTime createdOn;
    String description;
    @NotNull
    @EventDateConstraint
    LocalDateTime eventDate;
    Long id;
    @NotNull UserShortDto initiator;
    @NotNull LocationDto location;
    @NotNull Boolean paid;
    Integer participantLimit = 0;
    LocalDateTime publishedOn;
    Boolean requestModeration = false;
    EventState state;
    @NotBlank String title;
    Long views;
}
