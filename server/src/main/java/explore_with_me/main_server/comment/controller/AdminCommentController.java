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
 * Admin comment controller
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/comments")
@Slf4j
@Validated
public class AdminCommentController {

    private final CommentService commentService;

    /**
     * Delete comment by id
     * DELETE /admin/comments/{commentId}
     * @param commentId the comment id
     */

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long commentId) {
        log.info("Delete comment by id = {}", commentId);
        commentService.deleteByAdmin(commentId);
    }

    /**
     * Update comment by admin
     * PATCH /admin/comments/{commentId}
     * @param commentDto the comment dto
     * @param commentId  the comment id
     * @return the comment dto json
     */

    @PatchMapping("/{commentId}")
    public CommentReturnDto updateByAdmin(@Valid @RequestBody CommentDto commentDto,
                                          @PathVariable Long commentId) {
        log.info("Update comment by id = {}", commentId);
        return commentService.updateByAdmin(commentDto, commentId);
    }

    /**
     * Gets all comment by event
     * GET /admin/comments/{eventId}
     * @param eventId the event id
     * @param from    the from
     * @param size    the size
     * @return list comment json
     */

    @GetMapping("/{eventId}")
    public List<CommentReturnDto> getAll(@PathVariable Long eventId,
                                @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Get comments by eventId = {}", eventId);
        Pageable pageable = new OffsetBasedPageRequest(from, size, Sort.by("created").descending());
        return commentService.getAll(eventId, pageable);
    }
}