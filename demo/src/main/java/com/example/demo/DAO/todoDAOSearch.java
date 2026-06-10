package com.example.demo.DAO;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.todoEntity;


													//table, primaryKey
public interface todoDAOSearch extends JpaRepository<todoEntity, Long> {
	
	//SELECT * FROM todo WHERE LOWER(title) LIKE LOWER('%keyword%')     
	//% Wildcard → matches any number of characters (including none)
	
    List<todoEntity> findByTitleContainingIgnoreCase(String keyword);
}
