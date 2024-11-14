package com.diemendozac.budabingo.entities;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Getter
public class BingoCard {
	private final Map<String, int[]> cardNumbers;

	public BingoCard() {
		cardNumbers = generateCard();
	}

	private Map<String, int[]> generateCard() {
		Map<String, int[]> card = new HashMap<>();
		Random random = new Random();
		card.put("B", random.ints(5, 1, 16).toArray());
		card.put("I", random.ints(5, 16, 31).toArray());
		card.put("N", random.ints(4, 31, 46).toArray());
		card.put("G", random.ints(5, 46, 61).toArray());
		card.put("O", random.ints(5, 61, 76).toArray());
		return card;
	}

}
