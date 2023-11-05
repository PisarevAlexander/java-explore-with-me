package explore_with_me.main_server.user.controller;

import explore_with_me.main_server.user.model.User;
import explore_with_me.main_server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import explore_with_me.main_server.OffsetBasedPageRequest;
import explore_with_me.main_server.user.model.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * Admin user controller
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
@Slf4j
@Validated
public class AdminUserController {

    private final UserService userService;

    /**
     * Create user.
     * POST /admin/users
     * @param userDto the user dto
     * @return the user json
     */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@Valid @RequestBody UserDto userDto) {
        log.info("Create user = {}", userDto);
        return userService.create(userDto);
    }

    /**
     * Find all users
     * GET /admin/users
     * @param ids  the id list
     * @param from the from
     * @param size the size
     * @return the list of users json
     */

    @GetMapping
    public List<User> findAll(@RequestParam(required = false) List<Long> ids,
                              @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                              @Positive @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = new OffsetBasedPageRequest(from, size);
        log.info("Get users ids = {}", ids);
        return userService.getAll(ids, pageable);
    }

    /**
     * Delete user
     * DELETE /admin/users/{userId}
     * @param userId the user id
     */

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId) {
        log.info("Delete user by id = {}", userId);
        userService.delete(userId);
    }
}