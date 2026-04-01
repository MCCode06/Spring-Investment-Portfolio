package com.portfolio.spring_portfolio.service;

import com.portfolio.spring_portfolio.model.Investment;
import com.portfolio.spring_portfolio.repository.InvestmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvestmentService {

    private final InvestmentRepository investmentRepository;

    public List<Investment> getAllInvestments() {
        return investmentRepository.findAll();
    }

    public Investment getInvestmentById(Long id) {
        return investmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Investment not found with id: " + id));
    }

    public Investment addInvestment(Investment investment) {
        return investmentRepository.save(investment);
    }

    public Investment updateInvestmentName(Long id, String newName) {
        Investment existing = getInvestmentById(id);
        existing.setName(newName);
        return investmentRepository.save(existing);
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
        return 10_000_000.0 - getTotalInvested();
    }

    public List<Investment> getSortedInvestments(String sortBy) {
        return switch (sortBy) {
            case "amountAsc"  -> investmentRepository.findAllByOrderByAmountAsc();
            case "amountDesc" -> investmentRepository.findAllByOrderByAmountDesc();
            case "nameAsc"    -> investmentRepository.findAllByOrderByNameAsc();
            default           -> investmentRepository.findAll();
        };
    }

    public void addToFund(Double fund) {

    }
}
