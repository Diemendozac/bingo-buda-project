package com.diemendozac.budabingo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diemendozac.budabingo.services.GameService;
import com.diemendozac.budabingo.entities.BingoCard;

@RestController
@RequestMapping("/api/game")
public class GameController {

	@Autowired
	private GameService gameService;

	@GetMapping("/create-card")
	public BingoCard createCard() {
		return gameService.createBingoCard();
	}

	@PostMapping("/draw-number")
	public int drawNumber() {
		return gameService.drawNumber();
	}
}

