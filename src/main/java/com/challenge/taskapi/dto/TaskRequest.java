package com.challenge.taskapi.dto;

import com.challenge.taskapi.enums.Priority;
public class TaskRequest {

	private String title;
    private String description;
    private Priority priority;
    private Boolean completed;

	public TaskRequest(String title, String description, Priority priority, Boolean completed) {
		super();
		this.title = title;
		this.description = description;
		this.priority = priority;
		this.completed = completed;
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

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

}

