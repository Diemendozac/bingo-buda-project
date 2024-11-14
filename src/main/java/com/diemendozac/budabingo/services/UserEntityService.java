package com.diemendozac.budabingo.services;

import com.diemendozac.budabingo.entities.UserEntity;
import com.diemendozac.budabingo.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserEntityService {

	@Autowired
	private UserEntityRepository userEntityRepository;

	public UserEntity save(UserEntity userEntity) {
		return userEntityRepository.save(userEntity);
	}
	public Optional<UserEntity> findByUsername(String username) {
		return userEntityRepository.findByUsername(username);
	}

	public Optional<UserEntity> findById(String userId) {
		return userEntityRepository.findById(userId);
	}

	public void deleteUser(String userId) {
		userEntityRepository.deleteById(userId);
	}

}
