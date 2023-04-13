package webserver2.newproject.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import webserver2.newproject.board.dto.BoardPatchDto;
import webserver2.newproject.board.dto.BoardPostDto;
import webserver2.newproject.board.dto.BoardResponseDto;
import webserver2.newproject.board.entity.Board;
import webserver2.newproject.board.repository.BoardRepository;
import webserver2.newproject.exception.BusinessLogicException;
import webserver2.newproject.exception.ExceptionCode;
import webserver2.newproject.member.entity.Member;
import webserver2.newproject.member.repository.MemberRepository;
import webserver2.newproject.member.service.MemberService;


@Service
@RequiredArgsConstructor

public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberService memberService;



     //아이디 찾고 없으면 Exception
    public Board findBoardId(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(()->new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
    }

    public void isPermission(Member member, String email) {
        if (!member.getEmail().equals(email)) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
    }


    public Long createBoard(BoardPostDto boardPostDto) {
        Board board = new Board();

        board.setTitle(boardPostDto.getTitle());
        board.setContent(boardPostDto.getContent());
        board.setMember(memberService.displayNickname(boardPostDto.getMember()));

        return boardRepository.save(board).getBoardId();
    }

    public Long updateBoard(BoardPatchDto boardPatchDto, Long boardId,String email) {
        Board board = findBoardId(boardId);
        isPermission(board.getMember(),email);
        board.setTitle(boardPatchDto.getTitle());
        board.setContent(boardPatchDto.getContent());
        board.setMember(memberService.displayNickname(boardPatchDto.getMember()));


        return boardRepository.save(board).getBoardId();

    }

    public BoardResponseDto findByBoardId(Long boardId) {

        Board board = findBoardId(boardId);
        board.setBoardCount(board.getBoardCount() + 1);
//        board.setMember(displayNickname(boardPatchDto.getMember()));
        boardRepository.save(board); // 게시글의 조회수를 증가시킨 후 저장하는 용도
        return BoardResponseDto.FindFromBoard(board);
    }

    public Page<BoardResponseDto> findAllBoards(Pageable pageable) {
        Page<Board> boards = boardRepository.findAll(pageable);
        return boards.map(BoardResponseDto::FindFromBoard);
    }

    public void deleteBoard(Long boardId) {
        findBoardId(boardId);
        boardRepository.deleteById(boardId);
    }
}
