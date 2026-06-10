package com.example.demo.entity;
import jakarta.persistence.*;
import java.time.LocalDate;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



@Entity
@Table(name = "todoentity") 
public class todoEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title cannot be empty")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;
    private boolean completed;
    @FutureOrPresent(message = "Due date cannot be in the past")
    private LocalDate dueDate;  // Add this field

    // Getters and Setters
    
	 public LocalDate getDueDate() {
	     return dueDate;
	 }
	
	 public void setDueDate(LocalDate dueDate) {
	     this.dueDate = dueDate;
	 }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

