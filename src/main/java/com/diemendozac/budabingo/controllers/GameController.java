package com.diemendozac.budabingo.controllers;

import com.diemendozac.budabingo.entities.GameSession;
import com.diemendozac.budabingo.entities.UserEntity;
import com.diemendozac.budabingo.services.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
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
	@GetMapping("/create")
	public ResponseEntity<UUID> createGame(@RequestAttribute String username) {
		GameSession gameSession = gameService.createGameSession(username);
		return ResponseEntity.ok(gameSession.getId());
	}

	// Unirse a una partida con el ID
	@GetMapping("/{gameId}/join")
	public ResponseEntity<String> joinGame(@PathVariable String gameId, @RequestAttribute String username) {

		Optional<UserEntity> player = userEntityService.findByUsername(username);
		if (player.isEmpty()) return ResponseEntity.badRequest().build();
		gameService.connectToGame(gameId, player.get());
		return ResponseEntity.ok("Connected to game " + gameId);
	}

	@GetMapping("/{gameId}/start")
	public ResponseEntity<String> startGame(@PathVariable String gameId, @RequestAttribute String username) {
		gameService.startGame(gameId, username);
		return ResponseEntity.ok("Game " + gameId + " started!");
	}
}



