package explore_with_me.main_server.comment.controller;

import explore_with_me.main_server.OffsetBasedPageRequest;
import explore_with_me.main_server.comment.model.CommentReturnDto;
import explore_with_me.main_server.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
@Slf4j
@Validated
public class PublicCommentController {

    private final CommentService commentService;

    @GetMapping("/{eventId}")
    public List<CommentReturnDto> getAll(@PathVariable Long eventId,
                                         @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                         @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Get comments by eventId = {}", eventId);
        Pageable pageable = new OffsetBasedPageRequest(from, size, Sort.by("created").descending());
        return commentService.getAll(eventId, pageable);
    }
}
