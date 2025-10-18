package com.challenge.taskapi.dto;

import com.challenge.taskapi.enums.Priority;

import jakarta.validation.constraints.NotBlank;
public class TaskRequest {

	@NotBlank
	private String title;
	
	@NotBlank
    private String description;
	
    private Priority priority;

	public TaskRequest(String title, String description, Priority priority) {
		super();
		this.title = title;
		this.description = description;
		this.priority = priority;
	}

	public TaskRequest() {
		super();
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

}

