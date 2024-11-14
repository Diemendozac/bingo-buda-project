package com.diemendozac.budabingo.services;

import org.springframework.stereotype.Service;

import com.diemendozac.budabingo.entities.BingoCard;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class GameService {
	private final Set<Integer> drawnNumbers = new HashSet<>();
	private final Random random = new Random();

	public BingoCard createBingoCard() {
		return new BingoCard();
	}

	public int drawNumber() {
		int number;
		do {
			number = random.nextInt(75) + 1;
		} while (drawnNumbers.contains(number));
		drawnNumbers.add(number);
		return number;
	}

	public boolean checkWinner(BingoCard card, Set<Integer> markedNumbers) {
		// TODO: Lógica de validación
		return true;
	}

	public void resetGame() {
		drawnNumbers.clear();
	}
}

