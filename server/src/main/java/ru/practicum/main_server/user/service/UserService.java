package ru.practicum.main_server.user.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.main_server.user.model.User;
import ru.practicum.main_server.user.model.UserDto;

import java.util.List;

public interface UserService {

    User create(UserDto userDto);

    List<User> getAll(List<Long> ids, Pageable pageable);

    void delete(Long userId);
}
