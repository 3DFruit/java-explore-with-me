package ru.practicum.explorewithme.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    @NotNull
    private Double lat;
    @NotNull
    private Double lon;
}
