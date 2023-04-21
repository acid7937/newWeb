package webserver2.newproject.auth.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import webserver2.newproject.exception.BusinessLogicException;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler //이메일 이상하게 입력하면 뜰꺼임
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(e.getBindingResult()));
    }

    @ExceptionHandler // 글작성을 최소 얼마 이상 해야하는데 안하면 뜰꺼임
    public ResponseEntity handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(e.getConstraintViolations()));
    }

    @ExceptionHandler // 로그인했는데 또 로그인 하면 이거 뜰꺼다.
    public ResponseEntity handleBusinessLogicException(BusinessLogicException e) {
        return ResponseEntity.status(HttpStatus.valueOf(e.getExceptionCode().getStatus()))
                .body(ErrorResponse.of(e.getExceptionCode()));
    }

    @ExceptionHandler //POST 해야 되는데 PATCH나 DELETE 같은거 하면 뜬다.
    public ResponseEntity handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED));
    }

    @ExceptionHandler //비번 입력 안하고 전송하면 뜰꺼다.
    public ResponseEntity handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler //DB 오류 뜨면 뜰꺼다.
    public ResponseEntity handleException(Exception e) {
        log.error("# handle Exception", e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
