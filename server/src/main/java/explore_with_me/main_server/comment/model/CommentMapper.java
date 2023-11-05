package explore_with_me.main_server.comment.model;

/**
 * Comment mapper
 */

public class CommentMapper {

    /**
     * Comment to Comment Return DTO
     * @param comment the comment
     * @return the comment return DTO
     */

    public static CommentReturnDto toCommentReturnDto(Comment comment) {
        CommentReturnDto commentReturnDto = new CommentReturnDto();
        commentReturnDto.setId(comment.getId());
        commentReturnDto.setCreated(comment.getCreated());
        commentReturnDto.setInitiator(comment.getInitiator().getId());
        commentReturnDto.setEvent(comment.getEvent().getId());
        commentReturnDto.setText(comment.getText());
        return commentReturnDto;
    }
}
