package com.diemendozac.budabingo.repositories;

import com.diemendozac.budabingo.entities.GameSession;
import org.springframework.data.repository.CrudRepository;

public interface GameSessionRepository extends CrudRepository<GameSession, String> {
}
