package webserver2.newproject.board.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import webserver2.newproject.board.dto.BoardPatchDto;
import webserver2.newproject.board.dto.BoardPostDto;
import webserver2.newproject.board.dto.BoardResponseDto;
import webserver2.newproject.board.service.BoardService;
import webserver2.newproject.member.entity.Member;

@Getter @Setter
@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    @PostMapping("/post")
    public ResponseEntity postBoard(@RequestBody @Validated BoardPostDto boardPostDto) {

        Long boardId = boardService.createBoard(boardPostDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(boardId);
    }
    @PatchMapping("/{boardId}")
    public ResponseEntity patchBoard(@PathVariable("boardId")Long boardId,
                                     @RequestBody @Validated BoardPatchDto boardPatchDto,
                                     @AuthenticationPrincipal String email) {
        boardService.updateBoard(boardPatchDto, boardId,email);
        return ResponseEntity.status(HttpStatus.OK).body(boardId);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity getBoard(@PathVariable("boardId") Long boardId) {
        BoardResponseDto boardResponseDto = boardService.findByBoardId(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(boardResponseDto);
    }

    @GetMapping
    public ResponseEntity<Page<BoardResponseDto>> getAllBoards(
            @RequestParam(value = "page",defaultValue = "1")int page,
            @RequestParam(value = "size",defaultValue = "5")int size) {

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<BoardResponseDto> boards = boardService.findAllBoards(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(boards);
    }
    @DeleteMapping("/{boardId}")
    public ResponseEntity deleteBoard(@PathVariable("boardId") Long boardId,
                                      @AuthenticationPrincipal String email) {
        boardService.deleteBoard(boardId,email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

