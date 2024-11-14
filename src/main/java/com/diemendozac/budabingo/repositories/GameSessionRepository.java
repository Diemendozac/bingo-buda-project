package com.diemendozac.budabingo.repositories;

import com.diemendozac.budabingo.entities.GameSession;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface GameSessionRepository extends CrudRepository<GameSession, UUID> {
}
