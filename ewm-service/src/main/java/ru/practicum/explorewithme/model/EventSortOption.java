package ru.practicum.explorewithme.model;

import java.util.Optional;

public enum EventSortOption {
    EVENT_DATE, VIEWS;

    @Override
    public String toString() {
        return this.name();
    }

    public static Optional<EventSortOption> from(String state) {
        for (EventSortOption value : EventSortOption.values()) {
            if (value.name().equalsIgnoreCase(state)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }
}
