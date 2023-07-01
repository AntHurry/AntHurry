package com.ant.hurry.boundedContext.tradeStatus.code;

import com.ant.hurry.base.code.Code;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TradeStatusSuccessCode implements Code {

    CREATED_SUCCESS("S_T-1", "신규 거래가 생성되었습니다"),
    STATUS_CAN_UPDATE("S_T-2", "거래 상태를 변경할 수 있습니다."),
    STATUS_UPDATED("S_T-3", "거래 상태가 변경되었습니다."),
    REDIRECT_TO_PAGE("S_T-4", "거래 상태 페이지로 이동합니다.");


    public String code;
    public String message;
}
