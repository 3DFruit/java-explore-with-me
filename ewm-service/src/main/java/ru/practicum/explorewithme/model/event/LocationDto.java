package ru.practicum.explorewithme.model.event;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
public class LocationDto {
    @NotNull
    private Double lat;
    @NotNull
    private Double lon;
}
