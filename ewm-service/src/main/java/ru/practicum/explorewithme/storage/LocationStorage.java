package ru.practicum.explorewithme.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.model.event.Location;

import java.util.Optional;

@Repository
public interface LocationStorage extends JpaRepository<Location, Long> {
    Optional<Location> findByLatAndLon(Double lat, Double lon);
}
