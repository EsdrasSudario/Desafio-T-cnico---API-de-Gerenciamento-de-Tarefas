package com.challenge.taskapi.testAuthService;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.challenge.taskapi.dto.SignupRequest;
import com.challenge.taskapi.entity.Role;
import com.challenge.taskapi.entity.User;
import com.challenge.taskapi.service.AuthService;
import com.challenge.taskapi.repository.UserRepository;

@SpringBootTest
public class AuthServiceTest {
	
	@Autowired
	AuthService authService;
	
	@MockBean
	UserRepository userRepository;
	
	@MockBean
	PasswordEncoder encoder;

	//Ceanário de sucesso na criação de usuário
	@Test
	public void registerTest_Success(){
		// ARRANGE
		User user = new User();
		user.setId(1L);
		user.setUsername("name");
		user.setEmail("mail@mail");
		user.setPassword("123456");
		Set<Role> roles = new HashSet<>();
		user.setRoles(roles);
		user.setTasks(null);
		userRepository.save(user);
		Mockito.when(userRepository.existsByUsername(Mockito.anyString()))
			   .thenReturn(false);
		Mockito.when(encoder.encode( "password"))
		   .thenReturn(Mockito.anyString());
		Set<String> rolesSet = new HashSet<>();
		rolesSet.add(null);
		SignupRequest request = new SignupRequest("username","email@mail",rolesSet,"password");//role null

		//ACT
		ResponseEntity<?> responseEntity = authService.register(request);
		
		//ASSERT
		assertEquals(200, responseEntity.getStatusCode().value());		
	}
	
	@Test
	//Ceanário de erro na criação de usuário COM O MESMO NOME de usuário
	public void registerTest_Error(){
		// ARRANGE
		User user = new User();
		user.setId(1L);
		user.setUsername("name");
		user.setEmail("mail@mail");
		user.setPassword("123456");
		Set<Role> roles = new HashSet<>();
		user.setRoles(roles);
		user.setTasks(null);
		userRepository.save(user);
		Mockito.when(userRepository.existsByUsername(Mockito.anyString()))
			   .thenReturn(true);
		Mockito.when(encoder.encode( "password"))
		   .thenReturn(Mockito.anyString());
		Set<String> rolesSet = new HashSet<>();
		rolesSet.add(null);
		SignupRequest request = new SignupRequest("username","email@mail",rolesSet,"password");//role null

		//ACT
		ResponseEntity<?> responseEntity = authService.register(request);
		
		//ASSERT
		assertEquals(400, responseEntity.getStatusCode());		
	}
}
