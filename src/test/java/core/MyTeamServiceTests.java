package core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import db.*;
import exceptions.DropwizardException;
import org.junit.Test;
import api.MyPlayer;
import client.FplUtil;
import client.HttpUtil;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MyTeamServiceTests {
	@Test(expected = DropwizardException.class)
	public void getMyTeam_BadEmailPassword_ExceptionThrown() throws IOException, InterruptedException {
		HttpUtil mockedHttpUtil = mock(HttpUtil.class);
		HttpResponse<String> mockedHttpResponse = mock(HttpResponse.class);
		HttpHeaders mockedHttpHeaders = mock(HttpHeaders.class);
		when(mockedHttpUtil.sendPostRequest(any(), any())).thenReturn(mockedHttpResponse);
		when(mockedHttpResponse.headers()).thenReturn(mockedHttpHeaders);
		when(mockedHttpHeaders.firstValue(eq("location"))).thenReturn(Optional.of("state=fail"));

		ObjectMapper mockedObjectMapper = mock(ObjectMapper.class);

		ClubDAO mockedClubDAO = mock(ClubDAO.class);
		PlayerDAO mockedPlayerDAO = mock(PlayerDAO.class);

		FplUtil fplUtil = new FplUtil(mockedHttpUtil, mockedObjectMapper);
		MyTeamService myTeamService = new MyTeamService(mockedClubDAO, mockedPlayerDAO, fplUtil);

		// This call of the service should return a exceptions.DropwizardException.
		myTeamService.getMyTeam("bad_email", "bad_password");
	}

	@Test
	public void getMyTeam_GoodEmailPassword_TeamReturned() {
		JsonNode mockedTeamNode = getMockedTeamNode();
		FplUtil mockedFplUtil = mock(FplUtil.class);
		when(mockedFplUtil.getFixtures()).thenReturn(new Fixtures(Arrays.asList(new Fixture(0, 1))));
		when(mockedFplUtil.getUserID()).thenReturn("1997");
		when(mockedFplUtil.getUserTeam("1997")).thenReturn(mockedTeamNode);

		Club club = new Club("Chelsea", 0, 0);
		Club opposingClub = new Club("Spurs", 1, 1);
		Player player = new Player("Eden", "Hazard", 0, 3, 0, "5", 5, 5, 5, "5", "5", "5", "5", "5");

		ClubDAO mockedClubDAO = mock(ClubDAO.class);
		when(mockedClubDAO.getByCode(0)).thenReturn(Optional.of(club));
		when(mockedClubDAO.get(1)).thenReturn(Optional.of(opposingClub));

		PlayerDAO mockedPlayerDAO = mock(PlayerDAO.class);
		when(mockedPlayerDAO.get(0)).thenReturn(Optional.of(player));

		MyTeamService myTeamService = new MyTeamService(mockedClubDAO, mockedPlayerDAO, mockedFplUtil);

		MyPlayer expectedMyPlayer = new MyPlayer(
			"Eden Hazard",
			"Chelsea",
			"midfielder",
			true,
			true,
			false,
			"Spurs",
			true
		);

		assertThat(myTeamService.getMyTeam("good_email", "good_password").get(0))
			.usingRecursiveComparison().isEqualTo(expectedMyPlayer);
	}

	private JsonNode getMockedTeamNode() {
		JsonNode mockedTeamNode = mock(ObjectNode.class);
		JsonNode mockedPlayersNode = mock(ArrayNode.class);
		JsonNode mockedPlayerNode = mock(ObjectNode.class);
		JsonNode mockedElementNode = mock(ObjectNode.class);
		JsonNode mockedPositionNode = mock(ObjectNode.class);
		JsonNode mockedIsCaptainNode = mock(ObjectNode.class);
		JsonNode mockedIsViceCaptainNode = mock(ObjectNode.class);

		when(mockedTeamNode.get("picks")).thenReturn(mockedPlayersNode);
		when(mockedPlayersNode.iterator()).thenReturn(createPlayerNodeIterator(mockedPlayerNode));

		when(mockedPlayerNode.get("element")).thenReturn(mockedElementNode);
		when(mockedPlayerNode.get("position")).thenReturn(mockedPositionNode);
		when(mockedPlayerNode.get("is_captain")).thenReturn(mockedIsCaptainNode);
		when(mockedPlayerNode.get("is_vice_captain")).thenReturn(mockedIsViceCaptainNode);
		when(mockedElementNode.asInt()).thenReturn(0);
		when(mockedPositionNode.asInt()).thenReturn(0);
		when(mockedIsCaptainNode.asBoolean()).thenReturn(true);
		when(mockedIsViceCaptainNode.asBoolean()).thenReturn(false);

		return mockedTeamNode;
	}

	private Iterator<JsonNode> createPlayerNodeIterator(JsonNode playerNode) {
		List<JsonNode> playerNodes = new ArrayList();
		playerNodes.add(playerNode);
		return playerNodes.iterator();
	}
}
