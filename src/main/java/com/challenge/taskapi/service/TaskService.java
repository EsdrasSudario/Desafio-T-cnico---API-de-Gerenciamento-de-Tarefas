package com.challenge.taskapi.service;

import com.challenge.taskapi.dto.TaskRequestUp;
import com.challenge.taskapi.dto.TaskRequest;
import com.challenge.taskapi.dto.TaskResponse;
import com.challenge.taskapi.entity.Task;
import com.challenge.taskapi.entity.User;
import com.challenge.taskapi.enums.Priority;
import com.challenge.taskapi.exception.TaskNotFoundException;
import com.challenge.taskapi.repository.TaskRepository;
import com.challenge.taskapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public TaskResponse createTask(TaskRequest request, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority() != null ? request.getPriority() : Priority.MEDIUM);
        task.setCompleted(false);
        task.setUser(user);

        Task savedTask = taskRepository.save(task);
        return mapToResponse(savedTask);
    }

    public List<TaskResponse> getAllTasks(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<Task> tasks = taskRepository.findByUserId(user.getId());
        return tasks.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public TaskResponse getTaskById(Long id, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Task task = taskRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new TaskNotFoundException("Tarefa não encontrada com id: " + id));

        return mapToResponse(task);
    }

    public TaskResponse updateTask(Long id, TaskRequestUp requestUp, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Task task = taskRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new TaskNotFoundException("Tarefa não encontrada com id: " + id));

        if (requestUp.getTitle() != null) {
            task.setTitle(requestUp.getTitle());
        }
        if (requestUp.getDescription() != null) {
            task.setDescription(requestUp.getDescription());
        }
        if (requestUp.getPriority() != null) {
            task.setPriority(requestUp.getPriority());
        }
            task.setCompleted(requestUp.getCompleted());

        Task updatedTask = taskRepository.save(task);
        return mapToResponse(updatedTask);
    }

    public void deleteTask(Long id, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Task task = taskRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new TaskNotFoundException("Tarefa não encontrada com id: " + id));

        taskRepository.delete(task);
    }

    private TaskResponse mapToResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setPriority(task.getPriority());
        response.setCompleted(task.getCompleted());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());
        return response;
    }
}

