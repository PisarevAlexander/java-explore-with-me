package explore_with_me.main_server.comment.service;

import explore_with_me.main_server.comment.model.CommentDto;
import org.springframework.data.domain.Pageable;
import explore_with_me.main_server.comment.model.CommentReturnDto;

import java.util.List;

/**
 * The interface Comment service
 */

public interface CommentService {

    /**
     * Delete comment by admin.
     * @param commentId the comment id
     */

    void deleteByAdmin(Long commentId);

    /**
     * Update comment by admin
     * @param commentDto the comment dto
     * @param commentId  the comment id
     * @return the comment return dto
     */

    CommentReturnDto updateByAdmin(CommentDto commentDto, Long commentId);

    /**
     * Gets all comment
     * @param eventId  the event id
     * @param pageable the pageable
     * @return list of comments
     */

    List<CommentReturnDto> getAll(Long eventId, Pageable pageable);

    /**
     * Save comment
     * @param commentDto the comment dto
     * @param userId     the user id
     * @param eventId    the event id
     * @return the comment return DTO
     */

    CommentReturnDto save(CommentDto commentDto, Long userId, Long eventId);

    /**
     * Update comment by user
     * @param commentDto the comment dto
     * @param userId     the user id
     * @param commentId  the comment id
     * @return the comment return dto
     */

    CommentReturnDto updateByUser(CommentDto commentDto, Long userId, Long commentId);

    /**
     * Delete by user
     * @param userId    the user id
     * @param commentId the comment id
     */

    void deleteByUser(Long userId, Long commentId);
}
