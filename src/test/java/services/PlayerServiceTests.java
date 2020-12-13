package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.ClubDAO;
import dao.DAOInitialiser;
import dao.PlayerDAO;
import dropwizard.DropwizardException;
import entities.Club;
import entities.HttpSingleton;
import entities.Player;
import org.junit.Test;
import representations.Gameweek;
import representations.PlayerWithStats;
import representations.Season;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerServiceTests {
	private final int id = 0;
	private final String playerJson = new BufferedReader(
			new InputStreamReader(PlayerServiceTests.class.getResourceAsStream("/fixtures/Player.json")))
			.lines().collect(Collectors.joining("\n"));

	@Test
	public void getPlayer_PlayerExists_PlayerReturned() throws IOException, InterruptedException {
		PlayerService playerService = new PlayerService(mockDaoInitialiser(), mockHttpSingleton(), mockObjectMapper());

		assertThat(playerService.getPlayer("Olivier Giroud")).usingRecursiveComparison().isEqualTo
		(getExpectedPlayerWithStats());
	}

	@Test(expected = DropwizardException.class)
	public void getPlayer_PlayerDoesNotExist_ExceptionThrown() throws IOException, InterruptedException {
		PlayerService playerService = new PlayerService(mockEmptyDaoInitialiser(), mockHttpSingleton(),
				mockObjectMapper());
		playerService.getPlayer("not a name");
	}

	private DAOInitialiser mockDaoInitialiser() {
		DAOInitialiser mockedDaoInitialiser = mock(DAOInitialiser.class);

		PlayerDAO playerDao = new PlayerDAO();
		Player player = new Player(
				"Olivier",
				"Giroud",
				0,
				4,
				0,
				"5",
				5,
				5,
				5,
				"5",
				"5",
				"5",
				"5",
				"5"
		);
		playerDao.save(player);
		when(mockedDaoInitialiser.buildPlayerDao(any())).thenReturn(playerDao);

		ClubDAO clubDao = new ClubDAO();
		Club club = new Club("Chelsea", 0, 0);
		Club opposingClub = new Club("Spurs", 1, 1);
		clubDao.save(club);
		clubDao.save(opposingClub);
		when(mockedDaoInitialiser.buildClubDao(any())).thenReturn(clubDao);

		return mockedDaoInitialiser;
	}

	private HttpSingleton mockHttpSingleton() throws IOException, InterruptedException {
		HttpSingleton mockedHttpSingleton = mock(HttpSingleton.class);
		when(mockedHttpSingleton.sendGetRequest(
				String.format(
						"https://fantasy.premierleague.com/api/element-summary/%d/", id
				)
		)).thenReturn(playerJson);

		return mockedHttpSingleton;
	}

	private ObjectMapper mockObjectMapper() throws IOException {
		ObjectMapper mockedObjectMapper = mock(ObjectMapper.class);
		when(mockedObjectMapper.readValue(playerJson, PlayerWithStats.class)).thenReturn
		(getDeserialisedPlayerWithStats());

		return mockedObjectMapper;
	}

	private PlayerWithStats getDeserialisedPlayerWithStats() {
		PlayerWithStats playerWithStats = new PlayerWithStats(
				Arrays.asList(new Gameweek(
						1,
						7,
						1,
						0,
						0,
						0,
						1,
						3,
						1,
						0,
						"2020-09-14T19:15:00Z",
						0,
						"0.0",
						0,
						0,
						0,
						"0.0",
						70,
						75648,
						0,
						false,
						0,
						0,
						0,
						0,
						0,
						"0.0",
						"0.0",
						0
				)),
				Arrays.asList(new Season(
						"2012/13",
						90,
						73,
						121,
						2331,
						11,
						4,
						10,
						23,
						0,
						0,
						0,
						3,
						1,
						0,
						13,
						0,
						"0.0",
						"0.0",
						"0.0",
						"0.0"
				))
		);

		return playerWithStats;
	}

	private PlayerWithStats getExpectedPlayerWithStats() {
		PlayerWithStats playerWithStats = new PlayerWithStats(
				"Olivier Giroud",
				"Chelsea",
				"forward",
				Arrays.asList(new Gameweek(
						"Spurs",
						1,
						7,
						1,
						0,
						0,
						0,
						1,
						3,
						1,
						0,
						"2020-09-14T19:15:00Z",
						0,
						"0.0",
						0,
						0,
						0,
						"0.0",
						70,
						75648,
						0,
						false,
						0,
						0,
						0,
						0,
						0,
						"0.0",
						"0.0",
						0
				)),
				Arrays.asList(new Season(
						"2012/13",
						90,
						73,
						121,
						2331,
						11,
						4,
						10,
						23,
						0,
						0,
						0,
						3,
						1,
						0,
						13,
						0,
						"0.0",
						"0.0",
						"0.0",
						"0.0"
				))
		);

		return playerWithStats;
	}

	private DAOInitialiser mockEmptyDaoInitialiser() {
		DAOInitialiser mockedEmptyDaoInitialiser = mock(DAOInitialiser.class);
		when(mockedEmptyDaoInitialiser.buildPlayerDao(any())).thenReturn(new PlayerDAO());
		when(mockedEmptyDaoInitialiser.buildClubDao(any())).thenReturn(new ClubDAO());

		return mockedEmptyDaoInitialiser;
	}
}
