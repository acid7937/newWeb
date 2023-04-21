package webserver2.newproject.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberPatchDto {

    private String email;
    private String password;
    private String nickname;
}
