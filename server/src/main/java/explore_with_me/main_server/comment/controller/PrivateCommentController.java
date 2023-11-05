package explore_with_me.main_server.comment.controller;

import explore_with_me.main_server.OffsetBasedPageRequest;
import explore_with_me.main_server.comment.model.CommentDto;
import explore_with_me.main_server.comment.model.CommentReturnDto;
import explore_with_me.main_server.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * Private comment controller
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
@Validated
public class PrivateCommentController {

    private final CommentService commentService;

    /**
     * Create comment
     * POST /users/{userId}/comments/{eventId}
     * @param commentDto the comment dto
     * @param userId     the user id
     * @param eventId    the event id
     * @return comment dto json
     */

    @PostMapping("/{userId}/comments/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentReturnDto create(@Valid @RequestBody CommentDto commentDto, @PathVariable Long userId,
                                   @PathVariable Long eventId) {
        log.info("Create comment by userId = {} for eventId={}", userId, eventId);
        return commentService.save(commentDto, userId, eventId);
    }

    /**
     * Update comment by user
     * PATCH /users/{userId}/comments/{commentId}
     * @param commentDto the comment dto
     * @param userId     the user id
     * @param commentId  the comment id
     * @return comment dto json
     */

    @PatchMapping("/{userId}/comments/{commentId}")
    public CommentReturnDto updateByUser(@Valid @RequestBody CommentDto commentDto, @PathVariable Long userId,
                                @PathVariable Long commentId) {
        log.info("Update comment by id = {}", commentId);
        return commentService.updateByUser(commentDto, userId, commentId);
    }

    /**
     * Delete comment by user
     * DELETE /users/{userId}/comments/{commentId}
     * @param userId    the user id
     * @param commentId the comment id
     */

    @DeleteMapping("/{userId}/comments/{commentId}")
    public void delete(@PathVariable Long userId, @PathVariable Long commentId) {
        log.info("Delete comment by id = {}", commentId);
        commentService.deleteByUser(userId, commentId);
    }

    /**
     * Gets all comments by user
     * GET /users/comments/{eventId}
     * @param eventId the event id
     * @param from    the from
     * @param size    the size
     * @return list comments json
     */

    @GetMapping("/comments/{eventId}")
    public List<CommentReturnDto> getAll(@PathVariable Long eventId,
                                @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Get comments by eventId = {}", eventId);
        Pageable pageable = new OffsetBasedPageRequest(from, size, Sort.by("created").descending());
        return commentService.getAll(eventId, pageable);
    }
}