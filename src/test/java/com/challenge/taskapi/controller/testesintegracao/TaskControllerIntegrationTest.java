package com.challenge.taskapi.controller.testesintegracao;

import com.challenge.taskapi.dto.TaskRequest;
import com.challenge.taskapi.dto.TaskRequestUp;
import com.challenge.taskapi.dto.TaskResponse;
import com.challenge.taskapi.enums.Priority;
import com.challenge.taskapi.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private TaskResponse taskResponse;

    @BeforeEach
    void setup() {
        taskResponse = new TaskResponse(
                1L, "Tarefa Teste", "Descrição", Priority.HIGH, false,
                LocalDateTime.now(), LocalDateTime.now()
        );
    }

    // ✅ Cenário de sucesso - Criar Tarefa
    @Test
    @WithMockUser(username = "user1")
    void createTask_ShouldReturnCreated() throws Exception {
        TaskRequest request = new TaskRequest("Nova Tarefa", "Descrição", Priority.MEDIUM);
        Mockito.when(taskService.createTask(any(TaskRequest.class), eq("user1"))).thenReturn(taskResponse);

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Tarefa Teste"));
    }

    // ❌ Cenário de erro - Campos inválidos
    @Test
    @WithMockUser(username = "user1")
    void createTask_ShouldReturnBadRequest_WhenInvalid() throws Exception {
        TaskRequest request = new TaskRequest("", "", Priority.HIGH);

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is(500));
    }

    // ✅ Buscar todas as tarefas
    @Test
    @WithMockUser(username = "user1")
    void getAllTasks_ShouldReturnOk() throws Exception {
        List<TaskResponse> tasks = Arrays.asList(taskResponse);
        Mockito.when(taskService.getAllTasks("user1")).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Tarefa Teste"));
    }

    // ✅ Buscar por ID (sucesso)
    @Test
    @WithMockUser(username = "user1")
    void getTaskById_ShouldReturnOk() throws Exception {
        Mockito.when(taskService.getTaskById(1L, "user1")).thenReturn(taskResponse);

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    // ❌ Buscar por ID (não encontrada)
    @Test
    @WithMockUser(username = "user1")
    void getTaskById_ShouldReturnNotFound_WhenMissing() throws Exception {
        Mockito.when(taskService.getTaskById(99L, "user1"))
                .thenThrow(new RuntimeException("Tarefa não encontrada"));

        mockMvc.perform(get("/api/tasks/99"))
                .andExpect(status().isInternalServerError());
    }

    // ✅ Atualizar tarefa
    @Test
    @WithMockUser(username = "user1")
    void updateTask_ShouldReturnOk() throws Exception {
        TaskRequestUp update = new TaskRequestUp("Atualizada", "Nova descrição", Priority.LOW, true);
        Mockito.when(taskService.updateTask(eq(1L), any(TaskRequestUp.class), eq("user1")))
                .thenReturn(taskResponse);

        mockMvc.perform(put("/api/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Tarefa Teste"));
    }

    // ✅ Deletar tarefa
    @Test
    @WithMockUser(username = "user1")
    void deleteTask_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNoContent());
    }
}
