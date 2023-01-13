package com.lzl.handler;

import com.lzl.exception.SmsAuthException;
import com.lzl.exception.SmsBadRequestException;
import com.lzl.resp.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class SmsExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SmsAuthException.class)
    public ResponseEntity<Resp> authException(SmsAuthException smsAuthException){
        printErrLog(smsAuthException);
        Resp resp = new Resp(false,smsAuthException.getMessage() );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resp);
    }

    @ExceptionHandler(SmsBadRequestException.class)
    public ResponseEntity<Resp> authException(SmsBadRequestException smsBadRequestException){
        printErrLog(smsBadRequestException);
        Resp resp = new Resp(false,smsBadRequestException.getMessage() );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resp);
    }

    public void printErrLog(Exception e){
        log.error("异常：{}",e.getMessage());
    }
}
