package com.ant.hurry.chat.code;

import com.ant.hurry.base.code.Code;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatMessageErrorCode implements Code {

    MESSAGE_NOT_DELETED("F_G-1", "메시지 삭제에 실패했습니다."),
    MESSAGE_NOT_EXISTS("F_G-2", "존재하지 않는 메시지입니다."),
    FILE_NOT_EXISTS("F_G-3", "파일이 존재하지 않습니다."),
    URL_MALFORMED("F_G-4", "잘못된 URL입니다.");

    public String code;
    public String message;

}
