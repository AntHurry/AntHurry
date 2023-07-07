package com.ant.hurry.boundedContext.tradeStatus.repository;

import com.ant.hurry.boundedContext.member.entity.Member;
import com.ant.hurry.boundedContext.tradeStatus.entity.Status;
import com.ant.hurry.boundedContext.tradeStatus.entity.TradeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TradeStatusRepository extends JpaRepository<TradeStatus, Long> {
    @Query("select t from TradeStatus t join fetch t.requester r join fetch t.helper h where r.id = :memberId or h.id = :memberId")
    List<TradeStatus> findByRequesterOrHelper(@Param("memberId") Long memberId);

    @Query("select t from TradeStatus t join fetch t.requester r join fetch t.helper h where t.id = :id")
    Optional<TradeStatus> findById(@Param("id") Long id);

    @Query("select t from TradeStatus t join fetch t.requester r join fetch t.helper h " +
            "where (r.id = :memberId or h.id = :memberId)" +
            "and t.status = :status order by t.id desc")
    List<TradeStatus> findMyTradeStatus(@Param("memberId") Long memberId, @Param("status") Status status);

    @Query("select count(t.id) from TradeStatus t inner join t.requester r inner join t.helper h " +
            "where (r.id = :memberId or h.id = :memberId)" +
            "and t.status = com.ant.hurry.boundedContext.tradeStatus.entity.Status.COMPLETE")
    Long countMemberCompleteTradeStatus(@Param("memberId") Long memberId);


    @Query("select t from TradeStatus t join fetch t.board b join fetch t.helper h where b.id = :boardId and h.id = :memberId")
    Optional<TradeStatus> findByHelper(@Param("boardId") Long boardId, @Param("memberId") Long memberId);
}
