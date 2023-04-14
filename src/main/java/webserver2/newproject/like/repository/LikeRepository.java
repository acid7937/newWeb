package webserver2.newproject.like.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webserver2.newproject.board.entity.Board;
import webserver2.newproject.like.entity.Like;
import webserver2.newproject.member.entity.Member;

@Repository
public interface LikeRepository extends JpaRepository<Like,Long> {

    //있는지 없는지 검토
    boolean existsByMemberAndBoard(Member member, Board board);
    //삭제
    void deleteByMemberAndBoard(Member member, Board board);


}
