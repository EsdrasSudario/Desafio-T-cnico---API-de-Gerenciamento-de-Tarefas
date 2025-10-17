package com.challenge.taskapi.service;

import com.challenge.taskapi.dto.LoginRequest;
import com.challenge.taskapi.dto.LoginResponse;
import com.challenge.taskapi.dto.SignupRequest;
import com.challenge.taskapi.entity.Role;
import com.challenge.taskapi.entity.User;
import com.challenge.taskapi.repository.UserRepository;
import com.challenge.taskapi.security.JwtUtils;

import com.challenge.taskapi.repository.*;

import com.challenge.taskapi.enums.ERole;
import com.challenge.taskapi.payload.response.MessageResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	RoleRepository roleRepository;

    public ResponseEntity<?> login(LoginRequest request) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		return ResponseEntity.ok(new LoginResponse(userDetails.getId(), jwt, userDetails.getUsername(), roles));
    }

    public ResponseEntity<?> register(SignupRequest request) {
		if (userRepository.existsByUsername(request.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Erro: O nome do usuário já existe !"));
		}

		if (userRepository.existsByEmail(request.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Erro: Email já está em uso !"));
		}

		// Create new user's account
		User user = new User(request.getUsername(), request.getEmail(),
				encoder.encode( request.getPassword()) ) ;
		Set<String> strRoles = request.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Erro: Role não encontrado."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Erro: Role não encontrado."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Erro: Role não encontrado."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Erro: Role não encontrado."));
					roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("Usuário registrado com sucesso!"));

    }
}