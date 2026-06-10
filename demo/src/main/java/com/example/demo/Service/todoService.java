package com.example.demo.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.demo.DAO.todoDAO;
import com.example.demo.DAO.todoDAOSearch;
import com.example.demo.bean.todoBean;
import com.example.demo.entity.todoEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class todoService {

    @Autowired
    private todoDAO todoRepository;
    @Autowired
    private todoDAOSearch todoDAOSearch;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private EmailService emailService;

    public void addtodo(todoBean todoBean) {
        todoEntity todo = new todoEntity();
        todo.setTitle(todoBean.getTitle());
        todo.setCompleted(todoBean.isCompleted());
        todo.setDueDate(todoBean.getDueDate());
        todoRepository.save(todo);
    }
    
    
    private todoBean convertToBean(todoEntity todo) {
        todoBean bean = new todoBean();
        bean.setId(todo.getId());
        bean.setTitle(todo.getTitle());
        bean.setCompleted(todo.isCompleted());
        bean.setDueDate(todo.getDueDate());
        return bean;
    }

    public List<todoBean> getAlltodos() {
        List<todoEntity> todos = todoRepository.findAll();
        return todos.stream().map(this::convertToBean).collect(Collectors.toList());
    }

    
    public void toggleTodoStatus(Long id) {
        todoEntity todo = todoRepository.findById(id).orElseThrow();
        todo.setCompleted(!todo.isCompleted());
        todoRepository.save(todo);
    }
    
    //delete
    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }

    //update
    public void updateTodo(todoEntity updatedTodo) {
        // Optionally fetch and update specific fields here
        todoRepository.save(updatedTodo); // save() updates if id exists
    }


    public todoEntity getTodoById(Long id) {
        return todoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));
    }
    
    
    //search
    public List<todoBean> searchTodos(String keyword) {
    	List<todoEntity> todos =todoDAOSearch.findByTitleContainingIgnoreCase(keyword);
    	//System.out.println("Service method search todos : "+todos.get(0));
    	return todos.stream().map(this::convertToBean).collect(Collectors.toList());
    }
    
    //delete all
    @Transactional
    public void deleteAllTodos() {
        todoRepository.deleteAll();
    }
    
    //delete all completed
    @Transactional
    public void deleteAllCompleted() {
        todoRepository.deleteByCompletedTrue();
    }
    
   
   // @Scheduled(cron = "0 */1 * * * *")  // every 1 minute
    @Scheduled(cron = "0 0 8 * * *")   //cron means command run on
    public void sendReminders() {
        System.out.println("🕒 Scheduled task running at: " + LocalDateTime.now());

        List<todoEntity> todos = todoRepository.findAll();

        for (todoEntity todo : todos) {
            System.out.println("🔍 Task: " + todo.getTitle() +
                " | Due: " + todo.getDueDate() +
                " | Completed: " + todo.isCompleted());

            if (todo.getDueDate() != null && !todo.isCompleted()) {
                LocalDate today = LocalDate.now();
                LocalDate due = todo.getDueDate();

                if (due.equals(today)) {
                    System.out.println("📧 Sending reminder for task: " + todo.getTitle());

                    try {
                        emailService.sendReminderEmail(
                            "priyalghule@gmail.com",
                            "Reminder: " + todo.getTitle(),
                            "Your task \"" + todo.getTitle() + "\" is due today: " + due
                        );
                        System.out.println("✅ Reminder email sent");
                    } catch (Exception e) {
                        System.err.println("❌ Email sending failed: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
    

}


