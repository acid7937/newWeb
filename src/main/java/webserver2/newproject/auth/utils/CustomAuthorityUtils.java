package webserver2.newproject.auth.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomAuthorityUtils {

    private final List<GrantedAuthority> MEMBER_ROLES =
            AuthorityUtils.createAuthorityList("ROLE_MEMBER");

    public List<GrantedAuthority> createAuthorities(String email) {
        return MEMBER_ROLES;
    }

    public List<GrantedAuthority> createAuthorities(List<String> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    public List<String> createRoles(String email) {
        return MEMBER_ROLES.stream()
                .map(a -> a.getAuthority()
                        .replaceAll("ROLE_", ""))
                .collect(Collectors.toList());
    }
}
