package com.expensetracker.controller;



import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.expensetracker.entity.ExpenseTrackerItem;
import com.expensetracker.repository.ExpenseTrackerRepository;



@Controller
public class ExpenseTrackerItemController {
    private final Logger logger = LoggerFactory.getLogger(ExpenseTrackerItemController.class);

    @Autowired
    private ExpenseTrackerRepository expensesTrackerRepository;

    @GetMapping("/")
    public ModelAndView index() {
        logger.debug("root to GET index");
        ModelAndView modelAndView = new ModelAndView("index");
        List<ExpenseTrackerItem> expensesTrackerItems = (List<ExpenseTrackerItem>) expensesTrackerRepository.findAll();
        double totalPrice = expensesTrackerItems.stream().mapToDouble(ExpenseTrackerItem::getPrice).sum();
        modelAndView.addObject("ExpensesTrackerItems", expensesTrackerItems);
        modelAndView.addObject("today", Instant.now().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfWeek());
        modelAndView.addObject("totalPrice", totalPrice);
        modelAndView.addObject("averageDailyExpenses", calculateAverageDailyExpense(expensesTrackerItems));
        modelAndView.addObject("mostExpensiveCategory", findDominantCategory());
        return modelAndView;
    }

    @PostMapping("/ExpensesTracker")
    public String createExpensesTrackerItem(ExpenseTrackerItem expensesTrackerItem, BindingResult result) {
        if (result.hasErrors()) {
            return "add-ExpensesTracker-item";
        }
        expensesTrackerItem.setCreatedDate(Instant.now());
        expensesTrackerItem.setModifiedDate(Instant.now());
        expensesTrackerRepository.save(expensesTrackerItem);
        return "redirect:/";
    }
    
    @PostMapping("/ExpensesTracker/{id}")
    public String updateExpensesTrackerItem(@PathVariable("id") long id,ExpenseTrackerItem expensesTrackerItem, BindingResult result) {
        if (result.hasErrors()) {
            expensesTrackerItem.setId(id);
            return "update-ExpensesTracker-item";
        }
    
        expensesTrackerItem.setModifiedDate(Instant.now());
        expensesTrackerRepository.save(expensesTrackerItem);
        return "redirect:/";
    }
    
    private String findDominantCategory() {
        List<ExpenseTrackerItem> items = (List<ExpenseTrackerItem>) expensesTrackerRepository.findAll();
        return items.stream()
            .collect(Collectors.groupingBy(ExpenseTrackerItem::getCategory, Collectors.summingDouble(ExpenseTrackerItem::getPrice)))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
    }
    
    private double calculateTotalExpense() {
        List<ExpenseTrackerItem> expensesTrackerItems = (List<ExpenseTrackerItem>) expensesTrackerRepository.findAll();
        return expensesTrackerItems.stream().mapToDouble(ExpenseTrackerItem::getPrice).sum();
    }
    
    private double calculateAverageDailyExpense(List<ExpenseTrackerItem> expensesTrackerItems) {
        if (expensesTrackerItems.isEmpty()) {
            return 0.0;
        }
        LocalDate startDate = expensesTrackerItems.stream()
            .map(item -> item.getCreatedDate().atZone(ZoneId.systemDefault()).toLocalDate())
            .min(LocalDate::compareTo)
            .orElse(LocalDate.now());

        long days = ChronoUnit.DAYS.between(startDate, LocalDate.now());
        days = Math.max(days, 1);

        return calculateTotalExpense() / days;
    }
}
