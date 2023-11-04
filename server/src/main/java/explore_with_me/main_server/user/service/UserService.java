package explore_with_me.main_server.user.service;

import explore_with_me.main_server.user.model.User;
import org.springframework.data.domain.Pageable;
import explore_with_me.main_server.user.model.UserDto;

import java.util.List;

public interface UserService {

    User getById(Long userId);

    User create(UserDto userDto);

    List<User> getAll(List<Long> ids, Pageable pageable);

    void delete(Long userId);
}
