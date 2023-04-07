package webserver2.newproject.board.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class BoardPatchDto {
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
}
