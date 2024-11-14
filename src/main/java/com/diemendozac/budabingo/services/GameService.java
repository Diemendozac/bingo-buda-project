package com.diemendozac.budabingo.services;

import com.diemendozac.budabingo.entities.BingoCard;
import com.diemendozac.budabingo.entities.GameSession;
import com.diemendozac.budabingo.entities.UserEntity;
import com.diemendozac.budabingo.repositories.GameSessionRepository;
import com.diemendozac.budabingo.repositories.UserEntityRepository;
import com.diemendozac.budabingo.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;



@Service
public class GameService {

	@Autowired
	private GameSessionRepository gameSessionRepository;

	@Autowired
	private UserEntityRepository playerRepository;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public GameSession createGameSession(UUID creatorId) {
		GameSession gameSession = new GameSession(creatorId);
		gameSessionRepository.save(gameSession);
		return gameSession;
	}

	public GameSession connectToGame(UUID gameId, UserEntity player) {
		GameSession gameSession = gameSessionRepository.findById(String.valueOf(gameId))
						.orElseThrow(() -> new RuntimeException("Game not found"));
		gameSession.addPlayer(player);
		playerRepository.save(player);
		gameSessionRepository.save(gameSession);
		return gameSession;
	}

	public void startGame(UUID gameId, UUID creatorId) {
		GameSession gameSession = gameSessionRepository.findById(String.valueOf(gameId))
						.orElseThrow(() -> new RuntimeException("Game not found"));
		if (gameSession.canStart() && gameSession.getCreatorId().equals(creatorId)) {
			gameSession.startGame();
			gameSessionRepository.save(gameSession);

			// Inicia la emisión de números cada 10 segundos
			scheduler.scheduleAtFixedRate(() -> drawNumber(gameSession), 0, 10, TimeUnit.SECONDS);
		} else {
			throw new RuntimeException("Not enough players or unauthorized to start");
		}
	}

	private void drawNumber(GameSession gameSession) {
		if (!gameSession.isGameActive()) return;
		int number;
		do {
			number = new Random().nextInt(75) + 1;
		} while (gameSession.getDrawnNumbers().contains(number));

		gameSession.addDrawnNumber(number);
		gameSessionRepository.save(gameSession);
		messagingTemplate.convertAndSend("/topic/game/" + gameSession.getId(), number);
	}

	public boolean checkBingo(UUID gameId, BingoCard card) {
		GameSession gameSession = gameSessionRepository.findById(String.valueOf(gameId))
						.orElseThrow(() -> new RuntimeException("Game not found"));
		Set<Integer> markedNumbers = card.getMarkedNumbers();

		boolean isWinner = ValidationUtils.isWinningCombination(card, gameSession.getDrawnNumbers());

		if (isWinner) {
			endGame(gameSession);
		}

		return isWinner;
	}

	private void endGame(GameSession gameSession) {
		gameSession.setGameActive(false);
		gameSessionRepository.save(gameSession);
		messagingTemplate.convertAndSend("/topic/game/" + gameSession.getId(), "Game Over! We have a winner!");
		scheduler.shutdown();
	}
}



