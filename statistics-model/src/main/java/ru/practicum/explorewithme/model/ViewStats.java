package ru.practicum.explorewithme.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults
public class ViewStats {
    String app;
    String uri;
    Long hits;
}
