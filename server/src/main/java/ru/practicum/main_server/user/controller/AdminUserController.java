package ru.practicum.main_server.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_server.OffsetBasedPageRequest;
import ru.practicum.main_server.user.model.User;
import ru.practicum.main_server.user.model.UserDto;
import ru.practicum.main_server.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
@Slf4j
@Validated
public class AdminUserController {

    private final UserService userService;

    @PostMapping
    public User create(@Valid @RequestBody UserDto userDto) {
        log.info("Create user = {}", userDto);
        return userService.create(userDto);
    }

    @GetMapping
    public List<User> findAll(@RequestParam List<Long> ids,
                              @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                              @Positive @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = new OffsetBasedPageRequest(from, size);
        log.info("Get users ids = {}", ids);
        return userService.getAll(ids, pageable);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        log.info("Delete user by id = {}", userId);
        userService.delete(userId);
    }
}