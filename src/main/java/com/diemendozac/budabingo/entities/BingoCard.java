package com.diemendozac.budabingo.entities;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Entity
public class BingoCard {

	@Id
	@GeneratedValue
	private UUID id;

	@ElementCollection
	private Map<String, Set<Integer>> cardNumbers;

	@ElementCollection
	private Set<Integer> markedNumbers = new HashSet<>();

	public BingoCard() {
		this.cardNumbers = generateCard();
	}

	private Map<String, Set<Integer>> generateCard() {
		return Map.of(
						"B", generateUniqueNumbers(1, 15, 5),
						"I", generateUniqueNumbers(16, 30, 5),
						"N", generateUniqueNumbers(31, 45, 4), // Columna N con 4 números (centro vacío)
						"G", generateUniqueNumbers(46, 60, 5),
						"O", generateUniqueNumbers(61, 75, 5)
		);
	}

	private Set<Integer> generateUniqueNumbers(int min, int max, int count) {
		return IntStream.generate(() -> min + (int) (Math.random() * (max - min + 1)))
						.distinct()
						.limit(count)
						.boxed()
						.collect(Collectors.toSet());
	}

	public void markNumber(int number) {
		if (cardNumbers.values().stream().anyMatch(set -> set.contains(number))) {
			markedNumbers.add(number);
		}
	}
}
