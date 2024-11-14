package com.diemendozac.budabingo.controllers;

import com.diemendozac.budabingo.entities.BingoCard;
import com.diemendozac.budabingo.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameWebSocketController {

	@Autowired
	private GameService gameService;

	@MessageMapping("/check-bingo/{gameId}")
	@SendTo("/topic/game/{gameId}")
	public String checkBingo(@DestinationVariable String gameId, @Payload BingoCard card) {
		boolean isWinner = gameService.checkBingo(gameId, card);

		if (isWinner) {
			return "Congratulations! You have won the game!";
		} else {
			return "Incorrect Bingo! You have been disqualified.";
		}
	}
}
