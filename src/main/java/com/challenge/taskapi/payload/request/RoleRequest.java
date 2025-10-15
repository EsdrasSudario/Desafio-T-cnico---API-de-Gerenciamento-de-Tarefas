package com.challenge.taskapi.payload.request;

import java.util.Set;

//import jakarta.validation.constraints.NotBlank;

public class RoleRequest {
	//@NotBlank
	private Set<String> role;

	public Set<String> getRole() {
		return role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}

}
