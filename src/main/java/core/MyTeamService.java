package core;

import com.fasterxml.jackson.databind.JsonNode;
import db.ClubDAO;
import core.Fixtures;
import db.PlayerDAO;
import core.Club;
import core.Fixture;
import core.Player;
import api.MyPlayer;
import client.FplUtil;

import java.util.*;

public class MyTeamService {
	final Map<Integer, String> positionIdDict = new HashMap() {{
		put(1, "goalkeeper");
		put(2, "defender");
		put(3, "midfielder");
		put(4, "forward");
	}};

	ClubDAO clubDao;
	PlayerDAO playerDAO;
	FplUtil fplUtil;

	public MyTeamService(ClubDAO clubDao, PlayerDAO playerDAO, FplUtil fplUtil) {
		this.clubDao = clubDao;
		this.playerDAO = playerDAO;
		this.fplUtil = fplUtil;
	}

	public List<MyPlayer> getMyTeam(String email, String password) {
		fplUtil.authenticate(email, password);
		Fixtures fixtures = fplUtil.getFixtures();
		JsonNode teamNode = fplUtil.getUserTeam(fplUtil.getUserID());
		List<MyPlayer> myTeam = new ArrayList<>();
		for (JsonNode playerNode : teamNode.get("picks")) {
			// "element" is the key for player ID in the data returned from the FPL my-team endpoint.
			Player player = playerDAO.get(playerNode.get("element").asInt()).get();
			Club club = clubDao.getByCode(player.getClubCode()).orElse(new Club("UNKNOWN", -1, -1));

			Optional<Fixture> fixtureOptional = fixtures.getByClubId(club.getId());
			Club nextFixtureOpposingClub = new Club("N/A", -1, -1);
			boolean nextFixtureHome = false;
			if (fixtureOptional.isPresent()) {
				Fixture fixture = fixtureOptional.get();
				int nextFixtureOpposingClubId = fixture.getHomeTeamId();
				if (club.getId() == fixture.getHomeTeamId()) {
					nextFixtureHome = true;
					nextFixtureOpposingClubId = fixture.getAwayTeamId();
				}
				nextFixtureOpposingClub = clubDao.get(nextFixtureOpposingClubId).get();
			}

			MyPlayer myPlayer = new MyPlayer(
				player.getName(),
				club.getName(),
				positionIdDict.get(player.getPositionId()),
				playerNode.get("position").asInt() <= 11,
				playerNode.get("is_captain").asBoolean(),
				playerNode.get("is_vice_captain").asBoolean(),
				nextFixtureOpposingClub.getName(),
				nextFixtureHome
			);
			myTeam.add(myPlayer);
		}

		return myTeam;
	}
}
