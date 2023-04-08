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

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;



     //아이디 찾고 없으면 Exception
    public Board findBoardId(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(()->new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
    }

    public Long createBoard(BoardPostDto boardPostDto) {
        Board board = new Board();
        board.setTitle(boardPostDto.getTitle());
        board.setContent(boardPostDto.getContent());

        return boardRepository.save(board).getBoardId();
    }

    public Long updateBoard(BoardPatchDto boardPatchDto, Long boardId) {
        Board board = findBoardId(boardId);
        board.setTitle(boardPatchDto.getTitle());
        board.setContent(boardPatchDto.getContent());

        return boardRepository.save(board).getBoardId();

    }

    public BoardResponseDto findByBoardId(Long boardId) {

        Board board = findBoardId(boardId);
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
