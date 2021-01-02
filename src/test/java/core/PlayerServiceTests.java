package core;

import com.fasterxml.jackson.databind.ObjectMapper;
import db.ClubDAO;
import db.PlayerDAO;
import exceptions.DropwizardException;
import org.junit.Test;
import api.Gameweek;
import api.PlayerWithStats;
import api.Season;
import client.HttpUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerServiceTests {
	private final int id = 0;
	private final String playerJson = new BufferedReader(
			new InputStreamReader(PlayerServiceTests.class.getResourceAsStream("/fixtures/Player.json")))
			.lines().collect(Collectors.joining("\n"));

	@Test
	public void getPlayer_PlayerExists_PlayerReturned() throws IOException, InterruptedException {
		PlayerService playerService = new PlayerService(mockClubDAO(), mockPlayerDAO(), mockHttpUtil(),
			mockObjectMapper());

		assertThat(playerService.getPlayer("Olivier Giroud")).usingRecursiveComparison().isEqualTo
		(getExpectedPlayerWithStats());
	}

	@Test(expected = DropwizardException.class)
	public void getPlayer_PlayerDoesNotExist_ExceptionThrown() throws IOException, InterruptedException {
		PlayerService playerService = new PlayerService(mockEmptyClubDAO(), mockEmptyPlayerDAO(), mockHttpUtil(),
				mockObjectMapper());
		playerService.getPlayer("not a name");
	}

	private ClubDAO mockClubDAO() {
		Club club = new Club("Chelsea", 0, 0);
		Club opposingClub = new Club("Spurs", 1, 1);
		ClubDAO mockedClubDAO = mock(ClubDAO.class);
		when(mockedClubDAO.getByCode(0)).thenReturn(Optional.of(club));
		when(mockedClubDAO.get(1)).thenReturn(Optional.of(opposingClub));

		return mockedClubDAO;
	}

	private PlayerDAO mockPlayerDAO() {
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
		PlayerDAO mockedPlayerDAO = mock(PlayerDAO.class);
		when(mockedPlayerDAO.getByName("Olivier Giroud")).thenReturn(Optional.of(player));

		return mockedPlayerDAO;
	}

	private HttpUtil mockHttpUtil() throws IOException, InterruptedException {
		HttpUtil mockedHttpUtil = mock(HttpUtil.class);
		HttpResponse<String> mockedHttpResponse = mock(HttpResponse.class);
		when(mockedHttpResponse.body()).thenReturn(playerJson);
		when(mockedHttpUtil.sendGetRequest(
				String.format(
						"https://fantasy.premierleague.com/api/element-summary/%d/", id
				)
		)).thenReturn(mockedHttpResponse);

		return mockedHttpUtil;
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

	private ClubDAO mockEmptyClubDAO() {
		ClubDAO mockedClubDAO = mock(ClubDAO.class);
		when(mockedClubDAO.getByCode(anyInt())).thenReturn(Optional.empty());
		when(mockedClubDAO.get(anyInt())).thenReturn(Optional.empty());

		return mockedClubDAO;
	}

	private PlayerDAO mockEmptyPlayerDAO() {
		PlayerDAO mockedPlayerDAO = mock(PlayerDAO.class);
		when(mockedPlayerDAO.getByName(any())).thenReturn(Optional.empty());

		return mockedPlayerDAO;
	}
}
