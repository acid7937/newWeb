package webserver2.newproject.auth.dto;

import lombok.Data;

@Data//로그인 할때 쓸꺼고 Data는 getter setter 다 담고 있다해서 써봄 별 의미는 없음.
public class LoginDto {
    private String email;
    private String password;
}
