package explore_with_me.main_server.comment;

import explore_with_me.main_server.event.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import explore_with_me.main_server.comment.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByEvent(Event event, Pageable pageable);
}
