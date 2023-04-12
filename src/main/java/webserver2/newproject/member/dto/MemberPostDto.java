package webserver2.newproject.member.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberPostDto {
    @NotEmpty @Email
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String nickname;

}
