package explore_with_me.main_server.comment.service;

import explore_with_me.main_server.comment.CommentRepository;
import explore_with_me.main_server.comment.model.Comment;
import explore_with_me.main_server.comment.model.CommentDto;
import explore_with_me.main_server.comment.model.CommentMapper;
import explore_with_me.main_server.comment.model.CommentReturnDto;
import explore_with_me.main_server.event.model.Event;
import explore_with_me.main_server.event.service.EventService;
import explore_with_me.main_server.exception.BadRequestException;
import explore_with_me.main_server.exception.NotFoundException;
import explore_with_me.main_server.user.model.User;
import explore_with_me.main_server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Comment service
 */

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final EventService eventService;

    /**
     * Delete comment by admin.
     * @param commentId the comment id
     */

    @Override
    public void deleteByAdmin(Long commentId) {
        commentRepository.findById(commentId)
                .ifPresentOrElse(commentRepository::delete,
                        () -> {
                            throw new NotFoundException("Comment with Id=" + commentId + " not found");
                        });
    }

    /**
     * Update comment by admin
     * @param commentDto the comment dto
     * @param commentId  the comment id
     * @return the comment return dto
     */

    @Override
    public CommentReturnDto updateByAdmin(CommentDto commentDto, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("User with Id=" + commentId + " not found"));

        comment.setText(commentDto.getText());

        return CommentMapper.toCommentReturnDto(commentRepository.save(comment));
    }

    /**
     * Gets all comment
     * @param eventId  the event id
     * @param pageable the pageable
     * @return list of comments
     */

    @Override
    public List<CommentReturnDto> getAll(Long eventId, Pageable pageable) {
        Event event = eventService.getById(eventId);
        List<Comment> comments = commentRepository.findAllByEvent(event, pageable).getContent();

        if (comments.isEmpty()) {
            return new ArrayList<>();
        }
        return comments.stream()
                .map(CommentMapper::toCommentReturnDto)
                .collect(Collectors.toList());
    }

    /**
     * Save comment
     * @param commentDto the comment dto
     * @param userId     the user id
     * @param eventId    the event id
     * @return the comment return DTO
     */

    @Override
    public CommentReturnDto save(CommentDto commentDto, Long userId, Long eventId) {
        User user = userService.getById(userId);
        Event event = eventService.getById(eventId);

        Comment comment = new Comment();
        comment.setCreated(LocalDateTime.now());
        comment.setInitiator(user);
        comment.setEvent(event);
        comment.setText(commentDto.getText());
        return CommentMapper.toCommentReturnDto(commentRepository.save(comment));
    }

    /**
     * Update comment by user
     * @param commentDto the comment dto
     * @param userId     the user id
     * @param commentId  the comment id
     * @return the comment return dto
     */

    @Override
    public CommentReturnDto updateByUser(CommentDto commentDto, Long userId, Long commentId) {
        userService.getById(userId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("User with Id=" + commentId + " not found"));

        if (!userId.equals(comment.getInitiator().getId())) {
            throw new BadRequestException("Only initiator can update comment");
        }

        comment.setCreated(LocalDateTime.now());
        comment.setText(commentDto.getText());
        return CommentMapper.toCommentReturnDto(commentRepository.save(comment));
    }

    /**
     * Delete by user
     * @param userId    the user id
     * @param commentId the comment id
     */

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