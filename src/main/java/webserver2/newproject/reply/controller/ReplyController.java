package webserver2.newproject.reply.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import webserver2.newproject.board.service.BoardService;
import webserver2.newproject.reply.dto.ReplyPatchDto;
import webserver2.newproject.reply.dto.ReplyPostDto;
import webserver2.newproject.reply.dto.ReplyResponseDto;
import webserver2.newproject.reply.service.ReplyService;

@Setter @Getter
@RestController
@RequestMapping("/api/replies")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;
    private final BoardService boardService;

    @PostMapping("{boardId}")
    public ResponseEntity postReply(@RequestBody @Validated ReplyPostDto replyPostDto,
                                    @PathVariable("boardId") Long boardId) {
        Long replyId = replyService.createReply(replyPostDto, boardId);
        return ResponseEntity.status(HttpStatus.CREATED).body(replyId);
    }

    @PatchMapping("{replyId}")
    public ResponseEntity patchReply(@RequestBody ReplyPatchDto replyPatchDto,
                                     @PathVariable("replyId") Long replyId) {
        replyService.updateReply(replyPatchDto, replyId);
        return ResponseEntity.status(HttpStatus.OK).body(replyId);
    }

    @GetMapping("{boardId}")
    public ResponseEntity<Page<ReplyResponseDto>> getAllReply(
            @PathVariable("boardId") Long boardId,
            @RequestParam(value = "page",defaultValue = "1")int page,
            @RequestParam(value = "size",defaultValue = "5")int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ReplyResponseDto> replies = replyService.findAllReply(pageable,boardId);
        return ResponseEntity.status(HttpStatus.OK).body(replies);
    }

    @DeleteMapping("{replyId}")
    public ResponseEntity deleteReply(@PathVariable("replyId") Long replyId) {

        replyService.deleteReply(replyId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
