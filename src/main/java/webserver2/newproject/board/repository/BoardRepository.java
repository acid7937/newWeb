package webserver2.newproject.board.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webserver2.newproject.board.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {

}
