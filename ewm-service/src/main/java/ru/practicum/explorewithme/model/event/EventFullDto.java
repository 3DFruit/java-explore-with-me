package ru.practicum.explorewithme.model.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.explorewithme.model.category.CategoryDto;
import ru.practicum.explorewithme.model.user.UserShortDto;
import ru.practicum.explorewithme.utils.CommonUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventFullDto {
    @NotBlank String annotation;
    @NotNull CategoryDto category;
    Long confirmedRequests;
    @JsonFormat(pattern = CommonUtils.DATE_TIME_PATTERN)
    LocalDateTime createdOn;
    String description;
    @NotNull
    @EventDateConstraint
    @JsonFormat(pattern = CommonUtils.DATE_TIME_PATTERN)
    LocalDateTime eventDate;
    Long id;
    @NotNull UserShortDto initiator;
    @NotNull LocationDto location;
    @NotNull Boolean paid;
    Integer participantLimit = 0;
    @JsonFormat(pattern = CommonUtils.DATE_TIME_PATTERN)
    LocalDateTime publishedOn;
    Boolean requestModeration = false;
    EventState state;
    @NotBlank String title;
    Long views;
}
