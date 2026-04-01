package com.portfolio.spring_portfolio.controller;

import com.portfolio.spring_portfolio.model.Investment;
import com.portfolio.spring_portfolio.service.InvestmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/investments")
public class InvestmentController {

    private final InvestmentService investmentService;

    // HOME - list all investments
    @GetMapping
    public String listInvestments(
            @RequestParam(required = false, defaultValue = "default") String sortBy,
            Model model) {
        model.addAttribute("investments", investmentService.getSortedInvestments(sortBy));
        model.addAttribute("totalInvested", investmentService.getTotalInvested());
        model.addAttribute("remainingFund", investmentService.getRemainingFund());
        model.addAttribute("sortBy", sortBy);
        return "investments/list";
    }

    // DETAIL - show one investment
    @GetMapping("/{id}")
    public String getInvestment(@PathVariable Long id, Model model) {
        model.addAttribute("investment", investmentService.getInvestmentById(id));
        return "investments/detail";
    }

    // SHOW ADD FORM
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("investment", new Investment());
        return "investments/form";
    }

    // SUBMIT ADD FORM
    @PostMapping
    public String addInvestment(
            @Valid @ModelAttribute Investment investment,
            BindingResult result) {
        if (result.hasErrors()) {
            return "investments/form";
        }
        investmentService.addInvestment(investment);
        return "redirect:/investments";
    }

    // SHOW EDIT FORM
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("investment", investmentService.getInvestmentById(id));
        return "investments/edit";
    }

    // SUBMIT EDIT FORM
    @PostMapping("/{id}/edit")
    public String updateInvestment(
            @PathVariable Long id,
            @RequestParam String name) {
        investmentService.updateInvestmentName(id, name);
        return "redirect:/investments";
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String deleteInvestment(@PathVariable Long id) {
        investmentService.deleteInvestment(id);
        return "redirect:/investments";
    }

    // ADD TO FUND
    @PostMapping("/add-to-fund")
    public String addToFund(@RequestParam Double amount) {
        investmentService.addToFund(amount);
        return "redirect:/investments";
    }
}