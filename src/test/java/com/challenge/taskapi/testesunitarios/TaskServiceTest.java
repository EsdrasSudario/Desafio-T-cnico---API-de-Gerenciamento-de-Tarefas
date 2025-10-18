package com.challenge.taskapi.testesunitarios;

import com.challenge.taskapi.dto.TaskRequest;
import com.challenge.taskapi.dto.TaskRequestUp;
import com.challenge.taskapi.dto.TaskResponse;
import com.challenge.taskapi.entity.Task;
import com.challenge.taskapi.entity.User;
import com.challenge.taskapi.enums.Priority;
import com.challenge.taskapi.exception.TaskNotFoundException;
import com.challenge.taskapi.repository.TaskRepository;
import com.challenge.taskapi.repository.UserRepository;
import com.challenge.taskapi.service.TaskService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    private User user;
    private Task task;

    @BeforeEach
    void setup() {
        user = new User();
        user.setId(1L);
        user.setUsername("john");

        task = new Task();
        task.setId(10L);
        task.setTitle("Test Task");
        task.setDescription("Description");
        task.setPriority(Priority.HIGH);
        task.setCompleted(false);
        task.setUser(user);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
    }

    // ========================= createTask =========================

    @Test
    @DisplayName("createTask - sucesso")
    void createTask_Success() {
        TaskRequest request = new TaskRequest();
        request.setTitle("New Task");
        request.setDescription("New Description");
        request.setPriority(Priority.LOW);

        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponse response = taskService.createTask(request, "john");

        assertNotNull(response);
        assertEquals(task.getTitle(), response.getTitle());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("createTask - usuário não encontrado")
    void createTask_UserNotFound() {
        when(userRepository.findByUsername("john")).thenReturn(Optional.empty());

        TaskRequest request = new TaskRequest();
        request.setTitle("Task");

        assertThrows(RuntimeException.class, () -> taskService.createTask(request, "john"));
        verify(taskRepository, never()).save(any());
    }

    // ========================= getAllTasks =========================

    @Test
    @DisplayName("getAllTasks - sucesso")
    void getAllTasks_Success() {
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        when(taskRepository.findByUserId(user.getId())).thenReturn(Arrays.asList(task));

        List<TaskResponse> result = taskService.getAllTasks("john");

        assertEquals(1, result.size());
        assertEquals(task.getTitle(), result.get(0).getTitle());
    }

    @Test
    @DisplayName("getAllTasks - usuário não encontrado")
    void getAllTasks_UserNotFound() {
        when(userRepository.findByUsername("john")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> taskService.getAllTasks("john"));
    }

    // ========================= getTaskById =========================

    @Test
    @DisplayName("getTaskById - sucesso")
    void getTaskById_Success() {
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        when(taskRepository.findByIdAndUserId(task.getId(), user.getId())).thenReturn(Optional.of(task));

        TaskResponse response = taskService.getTaskById(task.getId(), "john");

        assertNotNull(response);
        assertEquals(task.getId(), response.getId());
    }

    @Test
    @DisplayName("getTaskById - tarefa não encontrada")
    void getTaskById_TaskNotFound() {
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        when(taskRepository.findByIdAndUserId(10L, user.getId())).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(10L, "john"));
    }

    // ========================= updateTask =========================

    @Test
    @DisplayName("updateTask - sucesso")
    void updateTask_Success() {
        TaskRequestUp requestUp = new TaskRequestUp();
        requestUp.setTitle("Updated Title");
        requestUp.setCompleted(true);

        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        when(taskRepository.findByIdAndUserId(task.getId(), user.getId())).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponse response = taskService.updateTask(task.getId(), requestUp, "john");

        assertNotNull(response);
        assertEquals("Updated Title", response.getTitle());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("updateTask - tarefa não encontrada")
    void updateTask_TaskNotFound() {
        TaskRequestUp requestUp = new TaskRequestUp();
        requestUp.setTitle("Update");

        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        when(taskRepository.findByIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(1L, requestUp, "john"));
    }

    // ========================= deleteTask =========================

    @Test
    @DisplayName("deleteTask - sucesso")
    void deleteTask_Success() {
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        when(taskRepository.findByIdAndUserId(task.getId(), user.getId())).thenReturn(Optional.of(task));

        taskService.deleteTask(task.getId(), "john");

        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    @DisplayName("deleteTask - tarefa não encontrada")
    void deleteTask_TaskNotFound() {
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        when(taskRepository.findByIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(1L, "john"));
        verify(taskRepository, never()).delete(any());
    }

    @Test
    @DisplayName("deleteTask - usuário não encontrado")
    void deleteTask_UserNotFound() {
        when(userRepository.findByUsername("john")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> taskService.deleteTask(1L, "john"));
    }
}
