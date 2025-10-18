package com.challenge.taskapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.challenge.taskapi.dto.SignupRequest;
import com.challenge.taskapi.service.AuthService;
import com.challenge.taskapi.service.UserDetailsImpl;
import com.challenge.taskapi.repository.UserRepository;
import com.challenge.taskapi.security.JwtUtils;
import com.challenge.taskapi.dto.LoginRequest;

@SpringBootTest
public class AuthServiceTest {

	@Autowired
	AuthService authService;

	@MockBean
	UserRepository userRepository;

	@MockBean
	AuthenticationManager authenticationManager;

	@MockBean
	JwtUtils jwtUtils;

	@MockBean
	PasswordEncoder encoder;
	
	@MockBean
	UsernamePasswordAuthenticationToken UsernamePasswordAuthenticationToken;
	
	// Ceanário de sucesso na criação de usuário
	@Test
	public void registerTest_Success() {
		// ARRANGE
		Mockito.when(userRepository.existsByUsername(Mockito.anyString())).thenReturn(false);
		Mockito.when(encoder.encode("password")).thenReturn(Mockito.anyString());
		Set<String> rolesSet = new HashSet<>();
		rolesSet.add("adm");
		SignupRequest request = new SignupRequest("username", "email@mail", rolesSet, "password");// ERole.ROLE_ADMIN

		// ACT
		ResponseEntity<?> responseEntity = authService.register(request);

		// ASSERT
		assertEquals(200, responseEntity.getStatusCode().value());
	}

	// Ceanário de erro na criação de usuário COM O MESMO NOME de usuário
	@Test
	public void registerTest_Error() {
		// ARRANGE
		Mockito.when(userRepository.existsByUsername("username")).thenReturn(true);
		Set<String> rolesSet = new HashSet<>();
		rolesSet.add(null);
		SignupRequest request = new SignupRequest("username", "email@mail", rolesSet, "123456");

		// ACT
		ResponseEntity<?> responseEntity = authService.register(request);

		// ASSERT
		assertEquals(400, responseEntity.getStatusCode().value());
	}

	//Cenário de erro no login de usuário
	@Test
	public void loginTest_Error() {
		//ARRENGE
		LoginRequest request = new LoginRequest("testusers", "password123");
		Mockito.when(authenticationManager.authenticate(Mockito.any(Authentication.class)))
        .thenThrow(new BadCredentialsException("Credenciais inválidas"));
		//ASSERT//ACT
		assertThrows(BadCredentialsException.class, () -> {
			authService.login(request);});
	}

	// Cenário de sucesso no login de usuário
	@Test
	public void loginTest_Success() {
		// ARANGE
		LoginRequest request = new LoginRequest("testuser", "password123");
		Authentication authentication = Mockito.mock(Authentication.class);
		UserDetailsImpl userDetails = Mockito.mock(UserDetailsImpl.class);
		Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
		Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
		// ACT
		ResponseEntity<?> responseEntity = authService.login(request);
		// ASSERT
		assertEquals(200, responseEntity.getStatusCode().value());
	}
}
