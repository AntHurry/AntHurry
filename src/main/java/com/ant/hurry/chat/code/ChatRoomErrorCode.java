package com.ant.hurry.chat.code;

import com.ant.hurry.base.code.Code;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatRoomErrorCode implements Code {

    CHATROOM_NO_EXISTS("F_R-1", "존재하지 않는 채팅방입니다."),
    CHATROOM_NOT_DELETED("F_R-2", "채팅방 삭제에 실패했습니다.");

    public String code;
    public String message;

}