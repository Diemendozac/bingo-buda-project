package com.diemendozac.budabingo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Entity
public class BingoCard {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(unique = true, nullable = false)
	private UUID id;

	@ElementCollection
	private Map<String, ArrayList<Integer>> cardNumbers;

	public BingoCard() {
		this.cardNumbers = generateCard();
	}

	private Map<String, ArrayList<Integer>> generateCard() {
		return Map.of(
						"B", generateUniqueNumbers(1, 15, 5),
						"I", generateUniqueNumbers(16, 30, 5),
						"N", generateUniqueNumbers(31, 45, 4), // Columna N con 4 números (centro vacío)
						"G", generateUniqueNumbers(46, 60, 5),
						"O", generateUniqueNumbers(61, 75, 5)
		);
	}

	private ArrayList<Integer> generateUniqueNumbers(int min, int max, int count) {
		return (ArrayList<Integer>) IntStream.generate(() -> min + (int) (Math.random() * (max - min + 1)))
						.distinct()
						.limit(count)
						.boxed()
						.collect(Collectors.toList());
	}
}
