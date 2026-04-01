package com.portfolio.spring_portfolio.service;

import com.portfolio.spring_portfolio.model.Fund;
import com.portfolio.spring_portfolio.model.Investment;
import com.portfolio.spring_portfolio.repository.FundRepository;
import com.portfolio.spring_portfolio.repository.InvestmentRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvestmentService {

    private final InvestmentRepository investmentRepository;
    private final FundRepository fundRepository;

    // PostConstruct annotation makes sure that there is a fun row with 10mil, if it doesn't exist, it creates new row
    @PostConstruct
    public void initFund() {
        if (fundRepository.findById(1L).isEmpty()) {
            fundRepository.save(new Fund(1L, 10_000_000.0));
        }
    }

    public Double getTotalFund() {
        return fundRepository.findById(1L)
                .map(Fund::getTotalFund)
                .orElse(10_000_000.0);
    }

    public void addToFund(Double amount) {
        Fund fund = fundRepository.findById(1L).
                orElse(new Fund(1L, 10_000_000.0));
        fund.setTotalFund(fund.getTotalFund() + amount);
        fundRepository.save(fund);
    }

    public List<Investment> getAllInvestments() {
        return investmentRepository.findAll();
    }

    public Investment getInvestmentById(Long id) {
        return investmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Investment not found with id: " + id));
    }

    public void addInvestment(Investment investment) {
        investmentRepository.save(investment);
    }

    public void updateInvestmentName(Long id, String newName) {
        Investment existing = getInvestmentById(id);
        existing.setName(newName);
        investmentRepository.save(existing);
    }

    public void deleteInvestment(Long id) {
        investmentRepository.deleteById(id);
    }

    public Double getTotalInvested() {
        return getAllInvestments()
                .stream()
                .mapToDouble(Investment::getAmount)
                .sum();
    }

    public Double getRemainingFund() {
        return getTotalFund() - getTotalInvested();
    }

    public List<Investment> getSortedInvestments(String sortBy) {
        return switch (sortBy) {
            case "amountAsc"  -> investmentRepository.findAllByOrderByAmountAsc();
            case "amountDesc" -> investmentRepository.findAllByOrderByAmountDesc();
            case "nameAsc"    -> investmentRepository.findAllByOrderByNameAsc();
            default           -> investmentRepository.findAll();
        };
    }
}