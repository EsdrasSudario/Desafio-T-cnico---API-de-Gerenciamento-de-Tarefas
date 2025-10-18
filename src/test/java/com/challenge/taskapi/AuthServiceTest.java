package com.challenge.taskapi;

import com.challenge.taskapi.dto.LoginRequest;
import com.challenge.taskapi.dto.LoginResponse;
import com.challenge.taskapi.dto.SignupRequest;
import com.challenge.taskapi.entity.Role;
import com.challenge.taskapi.entity.User;
import com.challenge.taskapi.enums.ERole;
import com.challenge.taskapi.payload.response.MessageResponse;
import com.challenge.taskapi.repository.RoleRepository;
import com.challenge.taskapi.repository.UserRepository;
import com.challenge.taskapi.security.JwtTokenProvider;
import com.challenge.taskapi.service.AuthService;
import com.challenge.taskapi.service.UserDetailsImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ Sucesso no cadastro
    @Test
    void testRegisterSuccess() {
        SignupRequest request = new SignupRequest("user1", "user1@email.com", Set.of("user"), "senha123");

        when(userRepository.existsByUsername("user1")).thenReturn(false);
        when(userRepository.existsByEmail("user1@email.com")).thenReturn(false);
        when(passwordEncoder.encode("senha123")).thenReturn("senhaCodificada");

        Role userRole = new Role(ERole.ROLE_USER);
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(userRole));

        ResponseEntity<?> response = authService.register(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Usuário registrado com sucesso!", ((MessageResponse) response.getBody()).getMessage());
        verify(userRepository).save(any(User.class));
    }

    // ❌ Nome de usuário já existe
    @Test
    void testRegisterUsernameExists() {
        SignupRequest request = new SignupRequest("user1", "user1@email.com", Set.of("user"), "senha123");

        when(userRepository.existsByUsername("user1")).thenReturn(true);

        ResponseEntity<?> response = authService.register(request);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Erro: O nome do usuário já existe !", ((MessageResponse) response.getBody()).getMessage());
        verify(userRepository, never()).save(any());
    }

    // ❌ Email já existe
    @Test
    void testRegisterEmailExists() {
        SignupRequest request = new SignupRequest("user2", "user2@email.com", Set.of("user"), "senha123");

        when(userRepository.existsByUsername("user2")).thenReturn(false);
        when(userRepository.existsByEmail("user2@email.com")).thenReturn(true);

        ResponseEntity<?> response = authService.register(request);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Erro: Email já está em uso !", ((MessageResponse) response.getBody()).getMessage());
        verify(userRepository, never()).save(any());
    }

    // ✅ Login com sucesso
    @Test
    void testLoginSuccess() {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setUsername("user");
        request.setPassword("senha");

        // Criação do User com role ROLE_USER
        Role role = new Role();
        role.setName(ERole.ROLE_USER);

        User user = new User();
        user.setId(1L);
        user.setUsername("user");
        user.setPassword("senhaCodificada");  // senha codificada mockada
        user.setEmail("user@email.com");
        user.setRoles(Set.of(role));

        // Criar UserDetailsImpl real usando build
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);

        // Simular autenticação
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Simular token JWT
        when(jwtTokenProvider.generateJwtToken(authentication)).thenReturn("fake-jwt-token");

        // Act
        ResponseEntity<?> response = authService.login(request);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertInstanceOf(LoginResponse.class, response.getBody());

        LoginResponse loginResponse = (LoginResponse) response.getBody();
        assertEquals("user", loginResponse.getUsername());
        assertEquals("fake-jwt-token", loginResponse.getToken());
        assertEquals(List.of("ROLE_USER"), loginResponse.getRoles());
    }

    // ❌ Falha de autenticação
    @Test
    void testLoginFailure() {
        LoginRequest request = new LoginRequest();
        request.setUsername("user");
        request.setPassword("senha");

        when(authenticationManager.authenticate(any()))
                .thenThrow(new RuntimeException("Credenciais inválidas"));

        assertThrows(RuntimeException.class, () -> authService.login(request));
    }
}
