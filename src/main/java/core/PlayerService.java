package core;

import com.fasterxml.jackson.databind.ObjectMapper;
import db.ClubDAO;
import db.PlayerDAO;
import exceptions.DropwizardException;
import client.HttpUtil;
import api.Gameweek;
import api.PlayerWithStats;

import java.util.HashMap;
import java.util.Map;

public class PlayerService {
	ClubDAO clubDAO;
	PlayerDAO playerDAO;
	HttpUtil httpUtil;
	ObjectMapper objectMapper;

	public PlayerService(ClubDAO clubDAO, PlayerDAO playerDAO, HttpUtil httpUtil, ObjectMapper objectMapper) {
		this.clubDAO = clubDAO;
		this.playerDAO = playerDAO;
		this.httpUtil = httpUtil;
		this.objectMapper = objectMapper;
	}

	public PlayerWithStats getPlayer(String name) {
		Player playerFromDao = playerDAO.getByName(name).orElseThrow(() -> new DropwizardException(String.format(
			"Player with name %s doesn't exist.", name)));
		Club clubFromDao = clubDAO.getByCode(playerFromDao.getClubCode()).orElse(new Club("UNKNOWN", 0, 0));

		return buildPlayerWithStats(playerFromDao, clubFromDao, clubDAO);
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
			String response = httpUtil.sendGetRequest(String.format("https://fantasy.premierleague" +
				".com/api/element-summary/%d/", id)).body();
			return objectMapper.readValue(response, PlayerWithStats.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DropwizardException("Something's gone wrong on our end.");
		}
	}
}
