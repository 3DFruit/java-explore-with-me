package ru.practicum.explorewithme.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.model.request.Request;

@Repository
public interface RequestStorage extends JpaRepository<Request, Long> {
}
