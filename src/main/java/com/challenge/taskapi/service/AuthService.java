package com.challenge.taskapi.service;

import com.challenge.taskapi.dto.LoginRequest;
import com.challenge.taskapi.dto.LoginResponse;
import com.challenge.taskapi.entity.User;
import com.challenge.taskapi.repository.UserRepository;
import com.challenge.taskapi.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }

        String token = tokenProvider.generateToken(user.getUsername());
        return new LoginResponse(token, user.getUsername());
    }

    public void register(LoginRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Usuário já existe");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());

        userRepository.save(user);
    }
}

