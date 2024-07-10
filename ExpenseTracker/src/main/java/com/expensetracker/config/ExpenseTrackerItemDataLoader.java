package com.expensetracker.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.expensetracker.entity.ExpenseTrackerItem;
import com.expensetracker.repository.ExpenseTrackerRepository;

@Component
public class ExpenseTrackerItemDataLoader implements CommandLineRunner {

	



	    private final Logger logger = LoggerFactory.getLogger(ExpenseTrackerItemDataLoader.class);

	    @Autowired
	    ExpenseTrackerRepository ExpensesTrackerRepository;

	    @Override
	    public void run(String... args) throws Exception {
	        loadSeedData();
	    }

	    private void loadSeedData() {
	        if (ExpensesTrackerRepository.count() == 0) {
	            ExpenseTrackerItem ExpensesTrackerItem1 = new ExpenseTrackerItem("milk", 50,"grocery");
	            ExpenseTrackerItem ExpensesTrackerItem2 = new ExpenseTrackerItem("leaves",20,"grocery");

	            ExpensesTrackerRepository.save(ExpensesTrackerItem1);
	            ExpensesTrackerRepository.save(ExpensesTrackerItem2); 
	        }

	        logger.info("Number of ExpensesTrackerItems: {}", ExpensesTrackerRepository.count());
	    }
	    
	}

