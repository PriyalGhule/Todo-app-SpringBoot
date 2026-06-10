package com.example.demo.DAO;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.todoEntity;


		
												//table, primaryKey
public interface todoDAO extends JpaRepository<todoEntity, Long> {
	
    //DELETE FROM todo WHERE completed = true;
	void deleteByCompletedTrue();
}





