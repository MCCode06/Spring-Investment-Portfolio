package com.portfolio.spring_portfolio.repository;

import com.portfolio.spring_portfolio.model.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Long> {

    List<Investment> findAllByOrderByAmountAsc();

    List<Investment> findAllByOrderByAmountDesc();

    List<Investment> findAllByOrderByNameAsc();
}