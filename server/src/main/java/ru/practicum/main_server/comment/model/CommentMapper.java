package ru.practicum.main_server.comment.model;

public class CommentMapper {

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
