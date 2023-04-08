package webserver2.newproject.reply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webserver2.newproject.reply.entity.Reply;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
