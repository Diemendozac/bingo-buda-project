package com.diemendozac.budabingo.config;

import com.diemendozac.budabingo.repositories.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
	private final UserEntityRepository userEntityRepository;

	@Bean
	public UserDetailsService userDetailsService() {
		return username ->
						userEntityRepository
										.findByUsername(username)
										.orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found."));
	}
}

