package ru.practicum.explorewithme.model.event;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explorewithme.model.category.CategoryDto;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewEventDto {
    @NotBlank
    @Size(min = 20, max = 2000)
    String annotation;
    @NotNull CategoryDto category;
    @NotNull
    @Size(min = 20, max = 7000)
    String description;
    @NotNull @EventDateConstraint LocalDateTime eventDate;
    @NotNull LocationDto location;
    Boolean paid = false;
    @PositiveOrZero Integer participantLimit = 0;
    Boolean requestModeration = false;
    @NotBlank
    @Size(min = 3, max = 120)
    String title;
}
