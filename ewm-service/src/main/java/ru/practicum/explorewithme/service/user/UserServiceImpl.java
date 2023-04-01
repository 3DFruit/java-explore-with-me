package ru.practicum.explorewithme.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.model.user.NewUserRequest;
import ru.practicum.explorewithme.model.user.UserDto;
import ru.practicum.explorewithme.model.user.UserMapper;
import ru.practicum.explorewithme.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;
    private final UserMapper mapper;

    @Override
    public List<UserDto> getUsersByIds(List<Long> ids, Pageable pageable) {
        return userStorage.getUsersByIdIn(ids, pageable).stream()
                .map(mapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto createUser(NewUserRequest userRequest) {
        return mapper.toUserDto(userStorage.save(mapper.toUser(userRequest)));
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        userStorage.deleteById(userId);
    }
}
