package com.ant.hurry.boundedContext.tradeStatus.service;

import com.ant.hurry.base.rsData.RsData;
import com.ant.hurry.boundedContext.board.entity.Board;
import com.ant.hurry.boundedContext.member.entity.Member;
import com.ant.hurry.boundedContext.member.service.MemberService;
import com.ant.hurry.boundedContext.review.service.ReviewService;
import com.ant.hurry.boundedContext.tradeStatus.entity.Status;
import com.ant.hurry.boundedContext.tradeStatus.entity.TradeStatus;
import com.ant.hurry.boundedContext.tradeStatus.repository.TradeStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ant.hurry.boundedContext.tradeStatus.entity.Status.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TradeStatusService {

    private final TradeStatusRepository tradeStatusRepository;
    private final MemberService memberService;

    @Transactional
    public TradeStatus create(Board board, Member requester, Member helper) {
        if (requester.equals(helper)) {
//            [ErrorCode] 본인의 게시글입니다.
        }
        TradeStatus tradeStatus = TradeStatus.builder()
                .status(BEFORE)
                .board(board)
                .requester(requester)
                .helper(helper)
                .build();
        tradeStatusRepository.save(tradeStatus);
        return tradeStatus;
    }

    @Transactional
    public TradeStatus updateStatus(TradeStatus tradeStatus, Status status) {
        if (!canUpdateStatus(tradeStatus, status)) {
//            [ErrorCode] return ErrorCode
            return null;
        }
        TradeStatus modifiedTradeStatus = tradeStatus.toBuilder()
                .status(status).build();
        tradeStatusRepository.save(modifiedTradeStatus);
        return modifiedTradeStatus;
    }

    /**
     * ErrorCode 생성 시 리턴 타입을 boolean -> ErrorCode로 변경
     * 원활한 테스트를 위해 임시로 예외 상황 시 false를 반환하도록 설정함
     * 상단의 updateStatus 부분도 변경 필요
     */
    private boolean canUpdateStatus(TradeStatus tradeStatus, Status status) {
        Status target = tradeStatus.getStatus();
        if (!target.equals(BEFORE) && status.equals(INPROGRESS)) {
//            [ErrorCode]진행 이력이 있는 거래입니다.
            return false;
        }
        if (!target.equals(INPROGRESS) && status.equals(COMPLETE)) {
//            [ErrorCode] 현재 진행 중인 거래만 완료할 수 있습니다.
            return false;
        }
        if(target.equals(COMPLETE) && !status.equals(CANCELED)) {
//         [ErrorCode] 이미 완료된 거래입니다.
            return false;
        }
        return true;
    }

    public List<TradeStatus> findByMember(Member member) {
        return tradeStatusRepository.findByRequesterOrHelper(member.getId());
    }

    public TradeStatus findById(Long id) {
        return tradeStatusRepository.findById(id).orElse(null);
    }

    public RsData<List<TradeStatus>> findMyTradeStatusList(String username, Status status) {

        Member member = memberService.findByUsername(username).orElse(null);

        if (member == null) {
            return RsData.of("F_M-1", "존재하지 않는 회원입니다.");
        }

        List<TradeStatus> tradeStatusList = tradeStatusRepository.findMyTradeStatus(member.getId(), status);

        return RsData.of("S_T-1", "거래상태페이지로 이동합니다.", tradeStatusList);
    }
}
