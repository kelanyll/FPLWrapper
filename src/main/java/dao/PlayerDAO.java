package dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import entities.Player;
import util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerDAO implements DAO<Player> {
	@JsonProperty("elements")
	private List<Player> players;

	// Jackson
	private PlayerDAO() {}

	public PlayerDAO(List<Player> players) {
		this.players = players;
	}

	@Override
	public Optional<Player> get(int id) {
		Player player = Util.findInList(
			players,
			possiblePlayer -> possiblePlayer.getId() == id
		);

		return Optional.ofNullable(player);
	}

	public Optional<Player> getByName(String name) {
		Player player = Util.findInList(
			players,
			possiblePlayer -> Util.sanitiseString(possiblePlayer.getName()).equals(Util.sanitiseString(name))
		);

		return Optional.ofNullable(player);
	}

	@Override
	public List<Player> getAll() {
		return players;
	}

	@Override
	public void save(Player player) {
		players.add(player);
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}
}
