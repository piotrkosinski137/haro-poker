package app.domain.game;

import java.util.List;

import app.domain.player.Player;

class Game {

	private final List<Player> players;
	private int entryFee;


	private Game(Player player) {
		players = List.of(player);
	}

	public int getEntryFee() {
		return entryFee;
	}

	public List<Player> getPlayers() {
		return players;
	}


	//private movePlayers


}
