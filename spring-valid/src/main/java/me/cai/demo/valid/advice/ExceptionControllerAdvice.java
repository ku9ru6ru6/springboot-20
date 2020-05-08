package me.cai.demo.valid.advice;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

/**
 * me.cai.demo.valid.advice
 *
 * @author caiguangzheng
 * date: 2020/5/7
 * mail: caiguangzheng@terminus.io
 * description:
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @Autowired
    private MessageSource messageSource;

    private final Locale locale = new Locale("zh", "CN");

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        // 从异常对象中拿到ObjectError对象
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        // 然后提取错误提示信息进行返回
        final String errorMessage = objectError.getDefaultMessage();
        String returnMessage;
        if (Strings.isNotBlank(errorMessage)) {
            returnMessage = messageSource.getMessage(errorMessage, null, locale);
        } else {
            final String defaultMessage = "server.error";
            returnMessage = messageSource.getMessage(defaultMessage, null, locale);
        }
        return ResponseEntity.badRequest().body(returnMessage);
    }
}
