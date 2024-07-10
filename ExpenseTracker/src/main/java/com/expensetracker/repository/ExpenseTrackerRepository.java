package com.expensetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expensetracker.entity.ExpenseTrackerItem;

public interface ExpenseTrackerRepository extends JpaRepository <ExpenseTrackerItem,Long>
{

}
