package ru.practicum.explorewithme.utils;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiError {
    String message;
    String reason;
    HttpStatus status;
    LocalDateTime timestamp;
    List<String> errors;
}
