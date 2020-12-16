package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.ClubDAO;
import dao.DAOInitialiser;
import dao.PlayerDAO;
import dropwizard.DropwizardException;
import entities.Club;
import entities.HttpSingleton;
import entities.Player;
import representations.Gameweek;
import representations.PlayerWithStats;

import java.util.HashMap;
import java.util.Map;

public class PlayerService {
	HttpSingleton httpSingleton;
	DAOInitialiser daoInitialiser;
	ObjectMapper objectMapper;

	public PlayerService(DAOInitialiser daoInitialiser, HttpSingleton httpSingleton, ObjectMapper objectMapper) {
		this.daoInitialiser = daoInitialiser;
		this.httpSingleton = httpSingleton;
		this.objectMapper = objectMapper;
	}

	public PlayerWithStats getPlayer(String name) {
		// We initialise the DAOs every time a request is made so that the
		// DAOs contain the most recent data. This is necessary until we can
		// persist the content of the DAOs in a database and periodically
		// check the FPL API for up-to-date data.
		PlayerDAO playerDao = daoInitialiser.buildPlayerDao();
		ClubDAO clubDao = daoInitialiser.buildClubDao();

		Player playerFromDao = playerDao.getByName(name).orElseThrow(() -> new DropwizardException(String.format(
			"Player with name %s doesn't exist.", name)));
		Club clubFromDao = clubDao.getByCode(playerFromDao.getClubCode()).orElse(new Club("UNKNOWN", 0, 0));

		return buildPlayerWithStats(playerFromDao, clubFromDao, clubDao);
	}

	private PlayerWithStats buildPlayerWithStats(Player playerFromDao, Club clubFromDao, ClubDAO clubDao) {
		PlayerWithStats playerWithStats = getPlayerAndDeserialise(playerFromDao.getId());
		playerWithStats.setName(playerFromDao.getName());
		playerWithStats.setClubName(clubFromDao.getName());
		playerWithStats.setPosition(positionIdDict.get(playerFromDao.getPositionId()));
		playerWithStats.getHistory().stream().forEach(
			(Gameweek gameweek) -> gameweek.setOpponent(
				clubDao.get(gameweek.getOpponent_team()).orElse(new Club("UNKNOWN", 0, 0))
					.getName()
			)
		);

		return playerWithStats;
	}

	final Map<Integer, String> positionIdDict = new HashMap<Integer, String>() {{
		put(1, "goalkeeper");
		put(2, "defender");
		put(3, "midfielder");
		put(4, "forward");
	}};

	private PlayerWithStats getPlayerAndDeserialise(int id) {
		try {
			String response = httpSingleton.sendGetRequest(String.format("https://fantasy.premierleague" +
				".com/api/element-summary/%d/", id));
			return objectMapper.readValue(response, PlayerWithStats.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DropwizardException("Something's gone wrong on our end.");
		}
	}
}
