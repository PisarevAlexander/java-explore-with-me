package ru.practicum.main_server.comment.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.main_server.comment.model.CommentDto;
import ru.practicum.main_server.comment.model.CommentReturnDto;

import java.util.List;

public interface CommentService {
    void deleteByAdmin(Long commentId);

    CommentReturnDto updateByAdmin(CommentDto commentDto, Long commentId);

    List<CommentReturnDto> getAll(Long eventId, Pageable pageable);

    CommentReturnDto save(CommentDto commentDto, Long userId, Long eventId);

    CommentReturnDto updateByUser(CommentDto commentDto, Long userId, Long commentId);

    void deleteByUser(Long userId, Long commentId);
}
