package com.diemendozac.budabingo.repositories;

import com.diemendozac.budabingo.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserEntityRepository extends CrudRepository<UserEntity, String> {
	Optional<UserEntity> findByUsername(String username);
	Optional<UserEntity> findById(UUID username);
}
