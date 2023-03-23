package ru.practicum.explorewithme.model.request;

import java.util.Optional;

public enum RequestState {
    PENDING, APPROVED, CANCELED, REJECTED;

    @Override
    public String toString() {
        return this.name();
    }

    public static Optional<ru.practicum.explorewithme.model.event.EventState> from(String state) {
        for (ru.practicum.explorewithme.model.event.EventState value : ru.practicum.explorewithme.model.event.EventState.values()) {
            if (value.name().equalsIgnoreCase(state)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }
}
