package webserver2.newproject.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import webserver2.newproject.board.entity.Board;
import webserver2.newproject.board.repository.BoardRepository;
import webserver2.newproject.board.service.BoardService;
import webserver2.newproject.like.entity.Like;
import webserver2.newproject.like.repository.LikeRepository;
import webserver2.newproject.member.entity.Member;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;

    public void addLike(Long boardId, Member member) {

        Board board = boardService.findBoardId(boardId);
        if (!likeRepository.existsByMemberAndBoard(member, board)) {
            // 호출되면 board에 있는 count 증가
            board.setLikeCount(board.getLikeCount()+1);
            // likeRepository에 memberId 값이랑 boardId값 저장해버림
            likeRepository.save(new Like(member, board));
        }
        else {
        board.setLikeCount(board.getLikeCount()-1);
        likeRepository.deleteByMemberAndBoard(member,board);
    }
//        boardRepository.save(board); //@Transactional 사용하니깐 더티 체킹으로 필요없어짐
    }
}
