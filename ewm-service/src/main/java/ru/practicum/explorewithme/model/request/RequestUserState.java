package ru.practicum.explorewithme.model.request;

import ru.practicum.explorewithme.model.event.EventState;

import java.util.Optional;

public enum RequestUserState {
    CONFIRMED, REJECTED;

    @Override
    public String toString() {
        return this.name();
    }

    public static Optional<EventState> from(String state) {
        for (ru.practicum.explorewithme.model.event.EventState value : ru.practicum.explorewithme.model.event.EventState.values()) {
            if (value.name().equalsIgnoreCase(state)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }
}
