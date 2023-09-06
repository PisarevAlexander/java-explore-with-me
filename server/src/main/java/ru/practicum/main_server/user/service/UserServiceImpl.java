package ru.practicum.main_server.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.main_server.exception.NotFoundException;
import ru.practicum.main_server.user.UserRepository;
import ru.practicum.main_server.user.model.User;
import ru.practicum.main_server.user.model.UserDto;
import ru.practicum.main_server.user.model.UserMapper;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Transactional
    @Override
    public User create(UserDto userDto) {
        return repository.save(UserMapper.toUser(userDto));
    }

    @Transactional
    @Override
    public List<User> getAll(List<Long> ids, Pageable pageable) {
        return repository.findAllByIdIn(ids, pageable).getContent();
    }

    @Transactional
    @Override
    public void delete(Long userId) {
        repository.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("User with Id=" + userId + " not found"));
        repository.deleteById(userId);
    }
}