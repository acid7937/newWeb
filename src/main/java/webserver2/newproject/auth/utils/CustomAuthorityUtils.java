package webserver2.newproject.auth.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component //이건 권한 처리 할려고 만듬
public class CustomAuthorityUtils {
    //GrantedAuthority가 인증된 사용자 정보 나타낼때 쓰이는 인터페이스다.
    private final List<GrantedAuthority> MEMBER_ROLES =
            AuthorityUtils.createAuthorityList("ROLE_MEMBER");//약속임 ROLE_MEMBER라고 서버내 에서 이름 짓는게


    //이메일 기준으로 권한 넣어줄꺼임
    public List<GrantedAuthority> createAuthorities(String email) {
        return MEMBER_ROLES;
    }

    ///권한을 스프링이 인식 하는데 사용될꺼임.
    public List<GrantedAuthority> createAuthorities(List<String> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    //여기서 원래는 ROLE_MEMBER로 DB에 저장되어야 하는데 깔끔하게 앞에 ROLE 삭제시킴 이유는 위에 설명
    public List<String> createRoles(String email) {
        return MEMBER_ROLES.stream()
                .map(a -> a.getAuthority()
                        .replaceAll("ROLE_", ""))
                .collect(Collectors.toList());
    }
}
