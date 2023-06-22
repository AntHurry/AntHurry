package com.ant.hurry.base.exception.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {

    MEMBER_NOT_EXISTS("존재하지 않는 회원입니다.");

    ErrorCode(String msg){
        this.msg = msg;
    }

    private String msg;
}
