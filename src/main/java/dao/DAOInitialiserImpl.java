package dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Club;
import entities.Fixture;
import entities.Player;
import util.FplUtilities;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DAOInitialiserImpl implements DAOInitialiser {
	private static final String BOOTSTRAP_URL = "https://fantasy.premierleague.com/api/bootstrap-static/";

	private HttpClient httpClient;
	private FplUtilities fplUtilities;

	public DAOInitialiserImpl(HttpClient httpClient, FplUtilities fplUtilities) {
		this.httpClient = httpClient;
		this.fplUtilities = fplUtilities;
	}

	public PlayerDAO buildPlayerDao(PlayerDAO playerDao) {
		JsonNode bootstrapNode = null;
		try {
			HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(BOOTSTRAP_URL))
				.build();
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			bootstrapNode = new ObjectMapper().readTree(response.body());
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (JsonNode playerNode : bootstrapNode.get("elements")) {
			Player player = new Player(
				playerNode.get("first_name").asText(),
				playerNode.get("second_name").asText(),
				playerNode.get("id").asInt(),
				playerNode.get("element_type").asInt(),
				playerNode.get("team_code").asInt(),
				playerNode.get("form").asText(),
				playerNode.get("now_cost").asInt(),
				playerNode.get("event_points").asInt(),
				playerNode.get("total_points").asInt(),
				playerNode.get("selected_by_percent").asText(),
				playerNode.get("influence").asText(),
				playerNode.get("creativity").asText(),
				playerNode.get("threat").asText(),
				playerNode.get("ict_index").asText()
			);
			playerDao.save(player);
		}

		return playerDao;
	}

	public ClubDAO buildClubDao(ClubDAO clubDao) {
		JsonNode bootstrapNode = null;
		try {
			HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(BOOTSTRAP_URL))
				.build();
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			bootstrapNode = new ObjectMapper().readTree(response.body());
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (JsonNode clubNode : bootstrapNode.get("teams")) {
			Club club = new Club(
				clubNode.get("name").asText(),
				clubNode.get("code").asInt(),
				clubNode.get("id").asInt()
			);
			clubDao.save(club);
		}

		return clubDao;
	}

	public FixtureDAO buildFixtureDao(FixtureDAO fixtureDao) {
		final String NEXT_FIXTURES_URL = "https://fantasy.premierleague.com/api/fixtures/?event="
			+ (fplUtilities.getCurrentGameweekId(fplUtilities.getUserId()) + 1);

		JsonNode fixturesNode = null;
		try {
			HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(NEXT_FIXTURES_URL))
				.build();
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			fixturesNode = new ObjectMapper().readTree(response.body());
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (JsonNode fixtureNode : fixturesNode) {
			Fixture fixture = new Fixture(
				fixtureNode.get("team_h").asInt(),
				fixtureNode.get("team_a").asInt()
			);
			fixtureDao.save(fixture);
		}

		return fixtureDao;
	}
}
