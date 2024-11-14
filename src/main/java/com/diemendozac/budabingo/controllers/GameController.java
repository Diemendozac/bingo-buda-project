package com.diemendozac.budabingo.controllers;

import com.diemendozac.budabingo.entities.GameSession;
import com.diemendozac.budabingo.entities.UserEntity;
import com.diemendozac.budabingo.services.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diemendozac.budabingo.services.GameService;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/game")
public class GameController {

	@Autowired
	private GameService gameService;
	@Autowired
	private UserEntityService userEntityService;

	// Crear una partida
	@PostMapping("/create")
	public ResponseEntity<UUID> createGame(@RequestParam UUID creatorId) {
		GameSession gameSession = gameService.createGameSession(creatorId);
		return ResponseEntity.ok(gameSession.getId());
	}

	// Unirse a una partida con el ID
	@PostMapping("/{gameId}/join")
	public ResponseEntity<String> joinGame(@PathVariable UUID gameId, @RequestParam UUID playerId) {
		Optional<UserEntity> player = userEntityService.findById(String.valueOf(playerId));
		if (player.isEmpty()) return ResponseEntity.badRequest().build();
		gameService.connectToGame(gameId, player.get());
		return ResponseEntity.ok("Connected to game " + gameId);
	}

	@PostMapping("/{gameId}/start")
	public ResponseEntity<String> startGame(@PathVariable UUID gameId, @RequestParam UUID creatorId) {
		gameService.startGame(gameId, creatorId);
		return ResponseEntity.ok("Game " + gameId + " started!");
	}
}



