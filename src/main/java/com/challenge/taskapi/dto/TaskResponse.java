package com.challenge.taskapi.dto;

import com.challenge.taskapi.enums.Priority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private Priority priority;
    private Boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

