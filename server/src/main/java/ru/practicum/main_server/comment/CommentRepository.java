package ru.practicum.main_server.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main_server.comment.model.Comment;
import ru.practicum.main_server.event.model.Event;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByEvent(Event event, Pageable pageable);
}
