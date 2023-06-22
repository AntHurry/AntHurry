package com.ant.hurry.base.exception.exceptions;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    public BusinessException(String msg){
        super(msg);
    }

    public BusinessException(ErrorCode errorCode){
        super(errorCode.getMsg());
    }

}
