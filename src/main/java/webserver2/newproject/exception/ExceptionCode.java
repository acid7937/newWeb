package webserver2.newproject.exception;

import lombok.Getter;

public enum ExceptionCode {


    BOARD_NOT_FOUND(400, "board not found"),
    MEMBER_NOT_FOUND(400, "member not found"),
    PASSWORD_NOT_FOUND(400, "password not found"),
    LOGIN_NOT_FOUND(400, "login not found"),
    NO_PERMISSION(400, "no permission"),
    REPLY_NOT_FOUND(400, "reply not found");





    @Getter
    private int status;
    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
