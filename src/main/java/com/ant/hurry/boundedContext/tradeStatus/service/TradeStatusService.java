package com.ant.hurry.boundedContext.tradeStatus.service;

import com.ant.hurry.boundedContext.board.entity.Board;
import com.ant.hurry.boundedContext.member.entity.Member;
import com.ant.hurry.boundedContext.tradeStatus.entity.Status;
import com.ant.hurry.boundedContext.tradeStatus.entity.TradeStatus;
import com.ant.hurry.boundedContext.tradeStatus.repository.TradeStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.ant.hurry.boundedContext.tradeStatus.entity.Status.*;

@Service
@RequiredArgsConstructor
public class TradeStatusService {

    private final TradeStatusRepository tradeStatusRepository;

    public TradeStatus create(Board board, Member requester, Member helper) {
        TradeStatus tradeStatus = TradeStatus.builder()
                .status(BEFORE)
                .board(board)
                .requester(requester)
                .helper(helper)
                .build();
        tradeStatusRepository.save(tradeStatus);
        return tradeStatus;
    }

    public TradeStatus updateStatus(TradeStatus tradeStatus, Status status) {
        if(!canUpdateStatus(tradeStatus, status)) {
//            [ErrorCode] return ErrorCode
        }
        TradeStatus modifiedTradeStatus = tradeStatus.toBuilder()
                .status(status).build();
        tradeStatusRepository.save(modifiedTradeStatus);
        return modifiedTradeStatus;
    }

    /**
     * ErrorCode 생성 시 리턴 타입을 boolean -> ErrorCode로 변경
     * 상단의 updateStatus 부분도 변경 필요
     */
    private boolean canUpdateStatus(TradeStatus tradeStatus, Status status) {
        Status target = tradeStatus.getStatus();
        if(!target.equals(BEFORE) && status.equals(INPROGRESS)) {
//            [ErrorCode]진행 이력이 있는 거래입니다.
        }
        if(!target.equals(INPROGRESS) && status.equals(COMPLETE)) {
//            [ErrorCode] 현재 진행 중인 거래만 완료할 수 있습니다.
        }
        if(target.equals(COMPLETE) && status.equals(CANCLED)) {
//            [ErrorCode] 이미 완료된 거래입니다.
        }
        return true;
    }
}
