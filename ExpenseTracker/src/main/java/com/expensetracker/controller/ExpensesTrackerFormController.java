package com.expensetracker.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.expensetracker.entity.ExpenseTrackerItem;
import com.expensetracker.repository.ExpenseTrackerRepository;

@Controller
public class ExpensesTrackerFormController {

    @Autowired
    private ExpenseTrackerRepository ExpensesTrackerRepository;

    @GetMapping("/create-expensestracker")
    public String showCreateForm(ExpenseTrackerItem expensesTrackerItem) {
        return "add-ExpensesTracker-item";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        ExpenseTrackerItem expensesTrackerItem = ExpensesTrackerRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ExpensesTrackerItem id: " + id + " not found"));
        model.addAttribute("ExpensesTracker", expensesTrackerItem);
        return "update-ExpensesTracker-item";
    }

    @GetMapping("/delete/{id}")
    public String deleteExpensesTrackerItem(@PathVariable("id") long id) {
        ExpenseTrackerItem expensesTrackerItem = ExpensesTrackerRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ExpensesTrackerItem id: " + id + " not found"));
        ExpensesTrackerRepository.delete(expensesTrackerItem);
        return "redirect:/";
    }
}