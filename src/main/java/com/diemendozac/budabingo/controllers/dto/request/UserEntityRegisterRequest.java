package com.diemendozac.budabingo.controllers.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntityRegisterRequest {

	@NotBlank
	@NotNull
	private String email;

	@NotBlank
	@NotNull
	@Length(min = 8)
	private String password;

	@NotBlank @NotNull
	private String name;
	@NotNull @NotBlank
	private String username;
}