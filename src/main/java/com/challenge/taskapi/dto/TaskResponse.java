package com.challenge.taskapi.dto;

import com.challenge.taskapi.enums.Priority;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskResponse {

	private Long id;
    private String title;
    private String description;
    private Priority priority;
    private Boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

	public TaskResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TaskResponse(Long id, String title, String description, Priority priority, Boolean completed,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.priority = priority;
		this.completed = completed;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Priority getPriority() {
		return priority;
	}
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	public Boolean getCompleted() {
		return completed;
	}
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

}

