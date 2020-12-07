package dao;

import entities.Player;
import util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerDAO implements DAO<Player> {
	private List<Player> players = new ArrayList<>();

	@Override
	public Optional<Player> get(int id) {
		Player player = Util.findInList(
			players,
			possiblePlayer -> possiblePlayer.getPlayerId() == id
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
}
