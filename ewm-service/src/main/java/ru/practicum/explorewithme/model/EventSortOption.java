package ru.practicum.explorewithme.model;

import ru.practicum.explorewithme.model.event.EventState;

import java.util.Optional;

public enum EventSortOption {
    EVENT_DATE, VIEWS;

    @Override
    public String toString() {
        return this.name();
    }

    public static Optional<EventState> from(String state) {
        for (EventState value : EventState.values()) {
            if (value.name().equalsIgnoreCase(state)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }
}
