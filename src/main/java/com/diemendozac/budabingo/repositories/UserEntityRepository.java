package com.diemendozac.budabingo.repositories;

import com.diemendozac.budabingo.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends CrudRepository<UserEntity, String> {
	Optional<UserEntity> findByEmail(String email);
	Optional<UserEntity> findByUsername(String email);
}
