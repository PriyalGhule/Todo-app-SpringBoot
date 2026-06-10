package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.EmailService;
import com.example.demo.Service.todoService;
import com.example.demo.bean.todoBean;
import com.example.demo.entity.todoEntity;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/todos")
public class todoController {

    private static final Logger logger =
            LoggerFactory.getLogger(todoController.class);

    @Autowired
    private todoService todoService;

    @Autowired
    private EmailService emailService;

    // TEST EMAIL
    @GetMapping("/test-email")
    public ResponseEntity<String> sendTestEmail() {

        emailService.sendReminderEmail(
                "priyalghule@gmail.com",
                "Test Email Subject",
                "This is a test email from Spring Boot"
        );

        logger.info("Email sent successfully");

        return ResponseEntity.ok("Email sent successfully");
    }

    // GET ALL TODOS
    @GetMapping
    public ResponseEntity<List<todoBean>> getTodos(
            @RequestParam(value = "keyword",
                    required = false) String keyword) {

        List<todoBean> todos;

        if (keyword != null && !keyword.isEmpty()) {

            todos = todoService.searchTodos(keyword);

        } else {

            todos = todoService.getAlltodos();
        }

        return ResponseEntity.ok(todos);
    }

    // ADD TODO
    @PostMapping
    public ResponseEntity<?> addTodo(
            @Valid @RequestBody todoBean todoBean,
            BindingResult result) {

        if (result.hasErrors()) {

            return ResponseEntity.badRequest()
                    .body(result.getAllErrors());
        }

        todoBean.setCompleted(false);

        todoService.addtodo(todoBean);

        logger.info("Todo item added successfully");

        return ResponseEntity.ok("Todo added successfully");
    }

    // TOGGLE TODO STATUS
    @PutMapping("/toggle/{id}")
    public ResponseEntity<String> toggleCompletion(
            @PathVariable Long id) {

        todoService.toggleTodoStatus(id);
        System.out.println("TOGGLE HIT");

        logger.info(
                "Todo item completion status changed successfully"
        );

        return ResponseEntity.ok(
                "Todo status updated successfully"
        );
    }

    // DELETE TODO
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(
            @PathVariable Long id) {

        todoService.deleteById(id);

        logger.info("Todo item deleted successfully");

        return ResponseEntity.ok(
                "Todo deleted successfully"
        );
    }

    // GET SINGLE TODO
    @GetMapping("/{id}")
    public ResponseEntity<todoEntity> getTodoById(
            @PathVariable Long id) {

        todoEntity todo = todoService.getTodoById(id);

        return ResponseEntity.ok(todo);
    }

    // UPDATE TODO
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTodo(
            @PathVariable Long id,
            @RequestBody todoEntity todo) {

        todo.setId(id);

        todoService.updateTodo(todo);

        logger.info("Todo item updated successfully");

        return ResponseEntity.ok(
                "Todo updated successfully"
        );
    }

    // DELETE ALL TODOS
    @DeleteMapping
    public ResponseEntity<String> deleteAllTodos() {

        todoService.deleteAllTodos();

        logger.info(
                "All Todo items deleted successfully"
        );

        return ResponseEntity.ok(
                "All todos deleted successfully"
        );
    }

    // DELETE COMPLETED TODOS
    @DeleteMapping("/completed")
    public ResponseEntity<String> deleteCompletedTodos() {

        todoService.deleteAllCompleted();

        logger.info(
                "Completed Todo items deleted successfully"
        );

        return ResponseEntity.ok(
                "Completed todos deleted successfully"
        );
    }

    // DOWNLOAD TODOS
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadTodos() {

        List<todoBean> todos =
                todoService.getAlltodos();

        StringBuilder content =
                new StringBuilder();

        for (todoBean todo : todos) {

            content.append("- ")
                    .append(todo.getTitle());

            if (todo.isCompleted()) {

                content.append(" [Completed]");
            }

            content.append("\n");
        }

        ByteArrayResource resource =
                new ByteArrayResource(
                        content.toString().getBytes()
                );

        logger.info(
                "Todo items downloaded successfully"
        );

        return ResponseEntity.ok()

                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=todos.txt"
                )

                .contentType(MediaType.TEXT_PLAIN)

                .contentLength(
                        resource.contentLength()
                )

                .body(resource);
    }
}