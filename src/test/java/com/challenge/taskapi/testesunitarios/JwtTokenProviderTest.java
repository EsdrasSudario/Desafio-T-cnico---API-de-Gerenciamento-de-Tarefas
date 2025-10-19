package com.challenge.taskapi.testesunitarios;

import com.challenge.taskapi.entity.Role;
import com.challenge.taskapi.entity.User;
import com.challenge.taskapi.enums.ERole;
import com.challenge.taskapi.security.JwtTokenProvider;
import com.challenge.taskapi.service.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    // Chave secreta codificada em Base64 (mínimo 256 bits para HMAC-SHA256)
    private final String secret = Base64.getEncoder().encodeToString(
            "minha-chave-secreta-super-segura-para-jwt-com-256-bits-no-minimo".getBytes()
    );

    // Configura a instância da JwtTokenProvider antes de cada teste
    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtSecret", secret);
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpirationMs", 3600000); // 1 hora
    }

    // Método auxiliar para criar um usuário falso com role
    private User createMockUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("usuario");
        user.setPassword("senha");

        Role role = new Role();
        role.setId(1);
        role.setName(ERole.ROLE_USER);

        user.setRoles(Set.of(role));
        return user;
    }

    // Testa se o token JWT é gerado com sucesso a partir de um usuário autenticado
    @Test
    void testGenerateJwtToken_Success() {
        User user = createMockUser();
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        String token = jwtTokenProvider.generateJwtToken(authentication);

        assertNotNull(token); // Verifica se o token não é nulo
        assertTrue(token.length() > 0); // Verifica se o token tem conteúdo
    }

    // Testa se o nome de usuário pode ser extraído corretamente de um token JWT válido
    @Test
    void testGetUserNameFromJwtToken_Success() {
        User user = createMockUser();
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        String token = jwtTokenProvider.generateJwtToken(authentication);
        String username = jwtTokenProvider.getUserNameFromJwtToken(token);

        assertEquals("usuario", username); // Verifica se o nome extraído é o esperado
    }

    // Testa a validação de um token válido — deve retornar true
    @Test
    void testValidateJwtToken_ValidToken_ReturnsTrue() {
        User user = createMockUser();
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        String token = jwtTokenProvider.generateJwtToken(authentication);
        boolean isValid = jwtTokenProvider.validateJwtToken(token);

        assertTrue(isValid); // Token válido deve ser aceito
    }

    // Testa a validação de um token expirado — deve retornar false
    @Test
    void testValidateJwtToken_ExpiredToken_ReturnsFalse() {
        // Cria um token que já está expirado
        String expiredToken = Jwts.builder()
                .setSubject("usuario")
                .setIssuedAt(new Date(System.currentTimeMillis() - 3600000)) // 1h atrás
                .setExpiration(new Date(System.currentTimeMillis() - 1000)) // 1 segundo atrás
                .signWith(jwtTokenProviderKey(), SignatureAlgorithm.HS256)
                .compact();

        boolean isValid = jwtTokenProvider.validateJwtToken(expiredToken);

        assertFalse(isValid); // Token expirado não deve ser aceito
    }

    // Testa a validação de um token malformado — deve retornar false
    @Test
    void testValidateJwtToken_MalformedToken_ReturnsFalse() {
        String malformedToken = "isso-nao-e-um-token-valido";

        boolean isValid = jwtTokenProvider.validateJwtToken(malformedToken);

        assertFalse(isValid); // Token inválido deve ser rejeitado
    }

    // Testa a validação de um token vazio — deve retornar false
    @Test
    void testValidateJwtToken_EmptyToken_ReturnsFalse() {
        boolean isValid = jwtTokenProvider.validateJwtToken("");

        assertFalse(isValid); // Token vazio não é válido
    }

    // Método auxiliar para recuperar a chave secreta da classe testada
    private Key jwtTokenProviderKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }
}
