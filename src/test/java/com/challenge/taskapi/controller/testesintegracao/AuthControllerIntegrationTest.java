package com.challenge.taskapi.controller.testesintegracao;

import com.challenge.taskapi.dto.LoginRequest;
import com.challenge.taskapi.dto.SignupRequest;
import com.challenge.taskapi.entity.Role;
import com.challenge.taskapi.enums.ERole;
import com.challenge.taskapi.repository.RoleRepository;
import com.challenge.taskapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    private String baseUrl;

    @BeforeEach
    void setup() {
        baseUrl = "http://localhost:" + port + "/api/auth";

        // Limpa o banco em cada teste
        userRepository.deleteAll();

        // Garante que os roles existem
        if (roleRepository.findAll().isEmpty()) {
            roleRepository.save(new Role(ERole.ROLE_USER));
            roleRepository.save(new Role(ERole.ROLE_ADMIN));
            roleRepository.save(new Role(ERole.ROLE_MODERATOR));
        }
    }

    @Test
    @DisplayName("❌ Deve falhar login com credenciais inválidas")
    void testLoginInvalidCredentials() {
        LoginRequest invalid = new LoginRequest("naoexiste", "senhaerrada");

        ResponseEntity<String> response = restTemplate.postForEntity(
                baseUrl + "/login",
                invalid,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("✅ Deve fazer login com sucesso após registro")
    void testLoginSuccess() {
        // Cria usuário primeiro
        SignupRequest signupRequest = new SignupRequest("lucas", "lucas@email.com", null, "senha123");
        restTemplate.postForEntity(baseUrl + "/register", signupRequest, String.class);

        // Faz login
        LoginRequest loginRequest = new LoginRequest("lucas", "senha123");

        ResponseEntity<String> response = restTemplate.postForEntity(
                baseUrl + "/login",
                loginRequest,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("token"); // Deve conter o JWT no JSON
    }

    @Test
    @DisplayName("✅ Deve registrar usuário com sucesso")
    void testRegisterSuccess() {
        SignupRequest signupRequest = new SignupRequest(
                "joao",
                "joao@email.com",
                new HashSet<>(Collections.singletonList("user")),
                "senha123"
        );

        ResponseEntity<String> response = restTemplate.postForEntity(
                baseUrl + "/register",
                signupRequest,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Usuário registrado com sucesso");

        assertThat(userRepository.existsByUsername("joao")).isTrue();
    }

    @Test
    @DisplayName("❌ Deve retornar erro ao registrar usuário com username duplicado")
    void testRegisterUsernameDuplicate() {
        // Primeiro cadastro
        SignupRequest first = new SignupRequest("maria", "maria@email.com", null, "senha123");
        restTemplate.postForEntity(baseUrl + "/register", first, String.class);

        // Segundo com o mesmo username
        SignupRequest duplicate = new SignupRequest("maria", "outra@email.com", null, "senha999");
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/register", duplicate, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Erro: O nome do usuário já existe");
    }

    @Test
    @DisplayName("❌ Deve retornar erro ao registrar usuário com email duplicado")
    void testRegisterEmailDuplicate() {
        // Primeiro cadastro
        SignupRequest first = new SignupRequest("ana", "ana@email.com", null, "senha123");
        restTemplate.postForEntity(baseUrl + "/register", first, String.class);

        // Segundo com o mesmo email
        SignupRequest duplicate = new SignupRequest("ana2", "ana@email.com", null, "senha999");
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/register", duplicate, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Erro: Email já está em uso");
    }

}
