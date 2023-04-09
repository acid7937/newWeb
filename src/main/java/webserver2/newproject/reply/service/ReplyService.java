package webserver2.newproject.reply.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import webserver2.newproject.board.entity.Board;
import webserver2.newproject.board.service.BoardService;
import webserver2.newproject.exception.BusinessLogicException;
import webserver2.newproject.exception.ExceptionCode;
import webserver2.newproject.reply.dto.ReplyPatchDto;
import webserver2.newproject.reply.dto.ReplyPostDto;
import webserver2.newproject.reply.dto.ReplyResponseDto;
import webserver2.newproject.reply.entity.Reply;
import webserver2.newproject.reply.repository.ReplyRepository;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final BoardService boardService;

    public Reply findReplyId(Long replyId) {
        return replyRepository.findById(replyId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.REPLY_NOT_FOUND));
    }

    public Long createReply(ReplyPostDto replyPostDto, Long boardId) {
        Board board = boardService.findBoardId(boardId);

        Reply reply = new Reply();
        reply.setReContent(replyPostDto.getReContent());
        reply.setBoard(board);

        return replyRepository.save(reply).getReplyId();
    }

    public Long updateReply(ReplyPatchDto replyPatchDto, Long replyId) {
        Reply reply = findReplyId(replyId);
        reply.setReContent(replyPatchDto.getReContent());

        return replyRepository.save(reply).getReplyId();
    }

    public Page<ReplyResponseDto> findAllReply(Pageable pageable,Long boardId) {
        Board board = boardService.findBoardId(boardId);
        Page<Reply> replies = replyRepository.findByBoard(board, pageable);
        return replies.map(ReplyResponseDto::FindFromReply);
    }

    public void deleteReply(Long replyId) {
        Reply reply = findReplyId(replyId);

        replyRepository.deleteById(replyId);
    }
}
