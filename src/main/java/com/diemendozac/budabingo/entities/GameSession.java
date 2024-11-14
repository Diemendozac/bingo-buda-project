package com.diemendozac.budabingo.entities;


import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GameSession {

	@Id
	@GeneratedValue
	private UUID id;

	private boolean gameActive = false;
	private UUID creatorId; // ID del jugador que creó la partida

	@OneToMany(cascade = CascadeType.ALL)
	private List<UserEntity> players = new ArrayList<>();

	@ElementCollection
	private Set<Integer> drawnNumbers = new HashSet<>();


	public GameSession(UUID creatorId) {
		this.creatorId = creatorId;
	}

	public void addPlayer(UserEntity player) {
		players.add(player);
	}

	public void addDrawnNumber(int number) {
		drawnNumbers.add(number);
	}

	public boolean canStart() {
		return players.size() > 1 && players.stream().anyMatch(p -> p.getId().equals(creatorId));
	}

	public void startGame() {
		this.gameActive = true;
	}
}


