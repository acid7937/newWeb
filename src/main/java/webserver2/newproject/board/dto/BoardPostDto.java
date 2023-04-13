package webserver2.newproject.board.dto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class BoardPostDto {

    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    private String member;
}
