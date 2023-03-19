package ru.practicum.explorewithme.model.event;

import java.util.Optional;

public enum UserStateAction {
    SEND_TO_REVIEW, CANCEL_REVIEW;

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
