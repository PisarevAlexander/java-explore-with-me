package explore_with_me.main_server.user.service;

import explore_with_me.main_server.user.UserRepository;
import explore_with_me.main_server.user.model.User;
import explore_with_me.main_server.user.model.UserDto;
import explore_with_me.main_server.user.model.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import explore_with_me.main_server.exception.ConflictException;
import explore_with_me.main_server.exception.NotFoundException;

import javax.transaction.Transactional;
import java.util.List;

/**
 * User service
 */

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    /**
     * Create user
     * @param userDto the user dto
     * @return the user
     */

    @Override
    public User create(UserDto userDto) {
        if (repository.findByName(userDto.getName()).isPresent()) {
            throw new ConflictException("Name must be unique");
        }
        return repository.save(UserMapper.toUser(userDto));
    }

    /**
     * Get by id.
     * @param userId the user id
     * @return the user
     */

    @Override
    public User getById(Long userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with Id=" + userId + " not found"));
    }

    /**
     * Delete user
     * @param userId the user id
     */

    @Override
    public List<User> getAll(List<Long> ids, Pageable pageable) {
        if (ids != null) {
            return repository.findAllByIdIn(ids, pageable).getContent();
        }
        return repository.findAll(pageable).getContent();
    }

    /**
     * Delete user
     * @param userId the user id
     */

    @Override
    public void delete(Long userId) {
        getById(userId);
        repository.deleteById(userId);
    }
}