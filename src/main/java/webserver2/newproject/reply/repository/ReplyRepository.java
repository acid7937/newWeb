package webserver2.newproject.reply.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webserver2.newproject.board.entity.Board;
import webserver2.newproject.reply.entity.Reply;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    Page<Reply> findByBoard(Board board, Pageable pageable);
}
