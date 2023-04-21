package webserver2.newproject.reply.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class ReplyPostDto {
    @NotEmpty
    private String reContent;
    private String member;


}
