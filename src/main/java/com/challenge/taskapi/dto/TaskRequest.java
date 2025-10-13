package com.challenge.taskapi.dto;

import com.challenge.taskapi.enums.Priority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {
    private String title;
    private String description;
    private Priority priority;
    private Boolean completed;
}

