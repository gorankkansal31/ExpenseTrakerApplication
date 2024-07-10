package com.expensetracker.entity;

import java.time.Instant;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity


@Table(name = "expenses")
public class ExpenseTrackerItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Integer price;

    @Column(name = "category")
    private String category;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "modified_date")
    private Instant modifiedDate;
    
    public ExpenseTrackerItem() {
        // Default constructor
    }

    public ExpenseTrackerItem(String description, Integer price, String category) {
        this.description = description;
        this.price = price;
        this.category = category;
        this.createdDate = Instant.now();
        this.modifiedDate = Instant.now();
    }
}
