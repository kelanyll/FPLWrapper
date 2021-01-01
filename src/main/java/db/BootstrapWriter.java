package db;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.DropwizardException;
import core.Club;
import client.HttpUtil;
import core.Player;

import java.io.IOException;

public class BootstrapWriter implements Runnable {
	private static final String BOOTSTRAP_URL = "https://fantasy.premierleague.com/api/bootstrap-static/";

	private final HttpUtil httpUtil;
	private final ObjectMapper objectMapper;
	private final ClubDAO clubDAO;
	private final PlayerDAO playerDAO;

	public BootstrapWriter(HttpUtil httpUtil, ObjectMapper objectMapper, ClubDAO clubDAO, PlayerDAO playerDAO) {
		this.httpUtil = httpUtil;
		this.objectMapper = objectMapper;
		this.clubDAO = clubDAO;
		this.playerDAO = playerDAO;
	}

	@Override
	public void run() {
		try {
			JsonNode bootstrapData = getBootstrapData();
			insertClubs(bootstrapData);
			insertPlayers(bootstrapData);
		} catch (FplUpdateException e) {
			System.out.println(e.getMessage());
		}
	}

	private JsonNode getBootstrapData() throws FplUpdateException {
		try {
			String responseBody = httpUtil.sendGetRequest(BOOTSTRAP_URL).body();
			if (responseBody.equals("The game is being updated.")) {
				throw new FplUpdateException("The game is currently being updated and so cannot fetch bootstrap data.");
			}
			return objectMapper.readTree(responseBody);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			throw new DropwizardException("Cannot connect to FPL API.");
		}
	}

	private void insertClubs(JsonNode bootstrapData) {
		System.out.println("Writing clubs to database from bootstrap data.");
		Club[] clubs = getBootstrapValue(bootstrapData, "teams", Club[].class);
		for (Club club : clubs) {
			clubDAO.insert(club);
		}
	}

	private <T> T getBootstrapValue(JsonNode bootstrapData, String attribute, Class classObject) {
		try {
			return (T) objectMapper.treeToValue(bootstrapData.get(attribute), classObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DropwizardException("Cannot deserialise bootstrap data.");
		}
	}

	private void insertPlayers(JsonNode bootstrapData) {
		System.out.println("Writing players to database from bootstrap data.");
		Player[] players = getBootstrapValue(bootstrapData, "elements", Player[].class);
		for (Player player : players) {
			playerDAO.insert(player);
		}
	}
}
