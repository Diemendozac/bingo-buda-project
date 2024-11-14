package com.diemendozac.budabingo.controllers;

import com.diemendozac.budabingo.controllers.dto.request.LoginRequest;
import com.diemendozac.budabingo.controllers.dto.request.UserEntityRegisterRequest;
import com.diemendozac.budabingo.controllers.dto.response.AuthenticationResponse;
import com.diemendozac.budabingo.controllers.utils.EmailValidator;
import com.diemendozac.budabingo.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
		AuthenticationResponse authenticationResponse = authService.login(request);
		String content = authenticationResponse.getToken();

		if (Objects.equals(content, "Username not found")) {
			return ResponseEntity.notFound().build();
		} else if (Objects.equals(content, "Incorrect user type")) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(authenticationResponse);
	}

	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> registerUser(
					@RequestBody UserEntityRegisterRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
	}
}
