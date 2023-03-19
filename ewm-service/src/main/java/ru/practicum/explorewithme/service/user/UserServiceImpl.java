package ru.practicum.explorewithme.service.user;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.model.user.NewUserRequest;
import ru.practicum.explorewithme.model.user.User;
import ru.practicum.explorewithme.model.user.UserDto;
import ru.practicum.explorewithme.storage.user.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    private final ObjectMapper mapper;

    @Autowired
    public UserServiceImpl(UserStorage userStorage) {
        this.userStorage = userStorage;
        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Override
    public List<UserDto> getUsersByIds(List<Long> ids, Pageable pageable) {
        return userStorage.getUsersByIdIn(ids, pageable).stream()
                .map(user -> mapper.convertValue(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto createUser(NewUserRequest userRequest) {
        return mapper.convertValue(
                userStorage.save(mapper.convertValue(userRequest, User.class)), UserDto.class
        );
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        userStorage.deleteById(userId);
    }
}
