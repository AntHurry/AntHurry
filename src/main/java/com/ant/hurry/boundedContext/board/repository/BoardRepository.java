package com.ant.hurry.boundedContext.board.repository;

import com.ant.hurry.boundedContext.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("SELECT b FROM Board b JOIN FETCH b.member WHERE b.id = :id")
    Optional<Board> findByIdWithMember(@Param("id") Long id);
}
