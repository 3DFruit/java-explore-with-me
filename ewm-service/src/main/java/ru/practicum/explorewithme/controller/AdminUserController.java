package ru.practicum.explorewithme.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.model.CustomPageRequest;
import ru.practicum.explorewithme.model.user.NewUserRequest;
import ru.practicum.explorewithme.model.user.UserDto;
import ru.practicum.explorewithme.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/users")
public class AdminUserController {
    UserService userService;

    @Autowired
    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getUsers(@RequestParam List<Long>ids, @RequestParam Integer from, @RequestParam Integer size) {
        return userService.getUsersByIds(ids, new CustomPageRequest(from, size, Sort.unsorted()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto postUser(@RequestBody NewUserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
