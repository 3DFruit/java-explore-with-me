package ru.practicum.explorewithme.model.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id", nullable = false)
    Long id;
    @Column(name = "created", nullable = false)
    LocalDateTime created;
    @Column(name = "event_id", nullable = false)
    Long event;
    @Column(name = "user_id", nullable = false)
    Long requester;
    @Column(name = "status", nullable = false)
    RequestState status;
}
