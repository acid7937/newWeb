package webserver2.newproject.auth.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
}
