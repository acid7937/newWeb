package webserver2.newproject.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import webserver2.newproject.member.entity.Member;

@Setter @Getter
@AllArgsConstructor
public class MemberResponseDto {

    private Long memberId;
    private String email;
    private String password;
    private String nickname;

    public static MemberResponseDto FindFromMember(Member member) {

        return new MemberResponseDto(
                member.getMemberId(),
                member.getEmail(),
                member.getPassword(),
                member.getNickname()
        );

    }
}
