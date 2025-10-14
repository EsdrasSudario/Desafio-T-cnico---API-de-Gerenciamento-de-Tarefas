package com.challenge.taskapi.service;

import com.challenge.taskapi.dto.LoginRequest;
import com.challenge.taskapi.dto.LoginResponse;
import com.challenge.taskapi.entity.User;
import com.challenge.taskapi.repository.UserRepository;
import com.challenge.taskapi.security.JwtUtils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils jwtUtils;

//    @Autowired
//    private JwtTokenProvider tokenProvider;

    public LoginResponse login(LoginRequest request) {
/*
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }

        String token = tokenProvider.generateToken(user.getUsername());
        return new LoginResponse(token, user.getUsername());
*/
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return new LoginResponse(userDetails.getId(), jwt, userDetails.getUsername(), roles);
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

