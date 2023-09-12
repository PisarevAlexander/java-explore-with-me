package ru.practicum.main_server.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_server.OffsetBasedPageRequest;
import ru.practicum.main_server.comment.model.CommentDto;
import ru.practicum.main_server.comment.model.CommentReturnDto;
import ru.practicum.main_server.comment.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
@Validated
public class PrivateCommentController {

    private final CommentService commentService;

    @PostMapping("/{userId}/comments/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentReturnDto create(@Valid @RequestBody CommentDto commentDto, @PathVariable Long userId,
                                   @PathVariable Long eventId) {
        log.info("Create comment by userId = {} for eventId={}", userId, eventId);
        return commentService.save(commentDto, userId, eventId);
    }

    @PatchMapping("/{userId}/comments/{commentId}")
    public CommentReturnDto updateByUser(@Valid @RequestBody CommentDto commentDto, @PathVariable Long userId,
                                @PathVariable Long commentId) {
        log.info("Update comment by id = {}", commentId);
        return commentService.updateByUser(commentDto, userId, commentId);
    }

    @DeleteMapping("/{userId}/comments/{commentId}")
    public void delete(@PathVariable Long userId, @PathVariable Long commentId) {
        log.info("Delete comment by id = {}", commentId);
        commentService.deleteByUser(userId, commentId);
    }

    @GetMapping("/comments/{eventId}")
    public List<CommentReturnDto> getAll(@PathVariable Long eventId,
                                @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Get comments by eventId = {}", eventId);
        Pageable pageable = new OffsetBasedPageRequest(from, size, Sort.by("created").descending());
        return commentService.getAll(eventId, pageable);
    }
}