package ru.practicum.main_server.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.main_server.comment.CommentRepository;
import ru.practicum.main_server.comment.model.Comment;
import ru.practicum.main_server.comment.model.CommentDto;
import ru.practicum.main_server.comment.model.CommentMapper;
import ru.practicum.main_server.comment.model.CommentReturnDto;
import ru.practicum.main_server.event.EventRepository;
import ru.practicum.main_server.event.model.Event;
import ru.practicum.main_server.exception.BadRequestException;
import ru.practicum.main_server.exception.NotFoundException;
import ru.practicum.main_server.user.UserRepository;
import ru.practicum.main_server.user.model.User;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public void deleteByAdmin(Long commentId) {
        commentRepository.delete(commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("User with Id=" + commentId + " not found")));
    }

    @Override
    public CommentReturnDto updateByAdmin(CommentDto commentDto, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("User with Id=" + commentId + " not found"));

        comment.setText(commentDto.getText());

        return CommentMapper.toCommentReturnDto(commentRepository.save(comment));
    }

    @Override
    public List<CommentReturnDto> getAll(Long eventId, Pageable pageable) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " not found"));
        List<Comment> comments = commentRepository.findAllByEvent(event, pageable).getContent();

        if (comments.isEmpty()) {
            return new ArrayList<>();
        }
        return comments.stream()
                .map(CommentMapper::toCommentReturnDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentReturnDto save(CommentDto commentDto, Long userId, Long eventId) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("User with Id=" + userId + " not found"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " not found"));

        Comment comment = new Comment();
        comment.setCreated(LocalDateTime.now());
        comment.setInitiator(user);
        comment.setEvent(event);
        comment.setText(commentDto.getText());
        return CommentMapper.toCommentReturnDto(commentRepository.save(comment));
    }

    @Override
    public CommentReturnDto updateByUser(CommentDto commentDto, Long userId, Long commentId) {
        userRepository.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("User with Id=" + userId + " not found"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("User with Id=" + commentId + " not found"));

        if (!userId.equals(comment.getInitiator().getId())) {
            throw new BadRequestException("Only initiator can update comment");
        }

        comment.setCreated(LocalDateTime.now());
        comment.setText(commentDto.getText());
        return CommentMapper.toCommentReturnDto(commentRepository.save(comment));
    }

    @Override
    public void deleteByUser(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("User with Id=" + commentId + " not found"));

        if (!userId.equals(comment.getInitiator().getId())) {
            throw new BadRequestException("Only initiator can delete comment");
        }

        commentRepository.delete(comment);
    }
}