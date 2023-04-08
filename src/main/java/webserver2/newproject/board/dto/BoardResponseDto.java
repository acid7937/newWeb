package webserver2.newproject.board.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import webserver2.newproject.board.entity.Board;
import webserver2.newproject.reply.dto.ReplyResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@AllArgsConstructor
public class BoardResponseDto {

    private Long boardId;
    private String title;
    private String content;
    private List<ReplyResponseDto> replies;

    //정적 팩토리 메서드 추가
    public static BoardResponseDto FindFromBoard(Board board) {

        List<ReplyResponseDto> replyResponseDtos = board.getReply().stream()
                .map(ReplyResponseDto::FindFromReply)
                .collect(Collectors.toList());

        return new BoardResponseDto(
                board.getBoardId(),
                board.getTitle(),
                board.getContent(),
                replyResponseDtos
        );
    }
}
