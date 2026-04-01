package com.portfolio.spring_portfolio.repository;

import com.portfolio.spring_portfolio.model.Fund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundRepository extends JpaRepository<Fund, Long> {
}