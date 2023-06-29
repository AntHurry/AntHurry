package com.ant.hurry.boundedContext.coin.repository;

import com.ant.hurry.boundedContext.coin.entity.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
    List<Exchange> findByStatusFalse();
}
