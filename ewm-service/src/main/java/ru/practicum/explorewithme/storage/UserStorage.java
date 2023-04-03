package ru.practicum.explorewithme.storage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.model.user.User;

import java.util.List;

@Repository
public interface UserStorage extends JpaRepository<User, Long> {
    Page<User> getUsersByIdIn(List<Long> ids, Pageable pageable);
}
