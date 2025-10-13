package com.challenge.taskapi.controller;

import com.challenge.taskapi.dto.TaskRequest;
import com.challenge.taskapi.dto.TaskResponse;
import com.challenge.taskapi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest request,
                                                     Authentication authentication) {
        String username = authentication.getName();
        TaskResponse response = taskService.createTask(request, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(Authentication authentication) {
        String username = authentication.getName();
        List<TaskResponse> tasks = taskService.getAllTasks(username);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id,
                                                      Authentication authentication) {
        String username = authentication.getName();
        TaskResponse response = taskService.getTaskById(id, username);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id,
                                                     @RequestBody TaskRequest request,
                                                     Authentication authentication) {
        String username = authentication.getName();
        TaskResponse response = taskService.updateTask(id, request, username);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id,
                                            Authentication authentication) {
        String username = authentication.getName();
        taskService.deleteTask(id, username);
        return ResponseEntity.noContent().build();
    }
}

