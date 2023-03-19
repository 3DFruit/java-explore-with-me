package ru.practicum.explorewithme.model.event;

import java.util.Optional;

public enum AdminStateAction {
    PUBLISH_EVENT, REJECT_EVENT;

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
