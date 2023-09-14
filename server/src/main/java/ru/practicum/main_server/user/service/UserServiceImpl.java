package ru.practicum.main_server.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.main_server.exception.ConflictException;
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

    @Override
    public User create(UserDto userDto) {
        if (repository.findByName(userDto.getName()).isPresent()) {
            throw new ConflictException("Name must be unique");
        }
        return repository.save(UserMapper.toUser(userDto));
    }

    @Override
    public User getById(Long userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with Id=" + userId + " not found"));
    }

    @Override
    public List<User> getAll(List<Long> ids, Pageable pageable) {
        if (ids != null) {
            return repository.findAllByIdIn(ids, pageable).getContent();
        }
        return repository.findAll(pageable).getContent();
    }

    @Override
    public void delete(Long userId) {
        getById(userId);
        repository.deleteById(userId);
    }
}