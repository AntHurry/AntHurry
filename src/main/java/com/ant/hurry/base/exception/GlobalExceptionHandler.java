package com.ant.hurry.base.exception;

import com.ant.hurry.base.exception.exceptions.BusinessException;
import com.ant.hurry.base.rq.Rq;
import com.ant.hurry.boundedContext.member.exception.NoSuchMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.util.stream.Collectors;


@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final Rq rq;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String errorHandler(MethodArgumentNotValidException exception) {
        String msg = exception
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("/"));
        return rq.historyBack(msg);
    }

    @ExceptionHandler({
            NoSuchMemberException.class,
    })
    public String handleNoSuchData(final NullPointerException e) {
        return rq.historyBack(e.getMessage());
    }

    @ExceptionHandler({BusinessException.class})
    public String handleNoSuchData(final RuntimeException e) {
        return rq.historyBack(e.getMessage());
    }

}
