package com.diemendozac.budabingo.services;

import com.diemendozac.budabingo.controllers.dto.request.LoginRequest;
import com.diemendozac.budabingo.controllers.dto.request.UserEntityRegisterRequest;
import com.diemendozac.budabingo.controllers.dto.response.AuthenticationResponse;
import com.diemendozac.budabingo.entities.UserEntity;
import com.diemendozac.budabingo.security.jwt.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
	private static final String USER_ID = "id";
	private final UserEntityService userEntityService;
	private final JwtUtils jwtUtils;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;

	public AuthenticationResponse login(LoginRequest request) {
		String username = request.getUsername();
		String password = request.getPassword();
		Optional<UserEntity> optUser = userEntityService.findByUsername(username);

		if (optUser.isEmpty()) {
			return AuthenticationResponse.builder().token("Username not found").build();
		}

		UserEntity user = optUser.get();

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		String jwtToken = jwtUtils.generateToken(Map.of(USER_ID, user.getUserId()), user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	public AuthenticationResponse register(UserEntityRegisterRequest request) {

		UserEntity user =
						UserEntity.builder()
										.username(request.getUsername())
										.email(request.getEmail())
										.password(passwordEncoder.encode(request.getPassword()))
										.name(request.getName())
										.build();

		UserEntity savedUser = userEntityService.save(user);

		String jwtToken = jwtUtils.generateToken(Map.of(USER_ID, savedUser.getUserId()), user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}
}
