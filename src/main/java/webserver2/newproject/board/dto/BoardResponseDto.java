package webserver2.newproject.board.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import webserver2.newproject.board.entity.Board;

@Getter @Setter
@AllArgsConstructor
public class BoardResponseDto {

    private Long boardId;
    private String title;
    private String content;

    //정적 팩토리 메서드 추가
    public static BoardResponseDto FindFromBoard(Board board) {

        return new BoardResponseDto(
                board.getBoardId(),
                board.getTitle(),
                board.getContent()

        );
    }
}
