package com.challenge.taskapi.dto;

import com.challenge.taskapi.enums.Priority;
public class TaskRequestUp {

	private String title;
    private String description;
    private Priority priority;
    private boolean completed;

	public TaskRequestUp(String title, String description, Priority priority, boolean completed) {
		super();
		this.title = title;
		this.description = description;
		this.priority = priority;
		this.completed = completed;
	}

	public TaskRequestUp() {
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

	public boolean getCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

}

