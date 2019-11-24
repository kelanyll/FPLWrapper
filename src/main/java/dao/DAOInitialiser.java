package dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Club;
import entities.Fixture;
import entities.Player;
import util.Util;

import java.io.IOException;

public class DAOInitialiser {
    private static final String BOOTSTRAP_URL = "https://fantasy.premierleague.com/api/bootstrap-static/";

    public DAOInitialiser(String email, String password) {
        Util.authenticate(email, password);
    }

    public PlayerDAO buildPlayerDao(PlayerDAO playerDao) {
        JsonNode bootstrapNode = null;
        try {
            bootstrapNode = new ObjectMapper().readTree(Util.sendGetRequest(BOOTSTRAP_URL));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (JsonNode playerNode: bootstrapNode.get("elements")) {
            Player player = new Player(
                    playerNode.get("first_name").asText(),
                    playerNode.get("second_name").asText(),
                    playerNode.get("id").asInt(),
                    playerNode.get("element_type").asInt(),
                    playerNode.get("team_code").asInt()
            );
            playerDao.save(player);
        }

        return playerDao;
    }

    public ClubDAO buildClubDao(ClubDAO clubDao) {
        JsonNode bootstrapNode = null;
        try {
            bootstrapNode = new ObjectMapper().readTree(Util.sendGetRequest(BOOTSTRAP_URL));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (JsonNode clubNode: bootstrapNode.get("teams")) {
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
                + (Util.getCurrentGameweekId(Util.getUserId()) + 1);

        JsonNode fixturesNode = null;
        try {
            fixturesNode = new ObjectMapper().readTree(Util.sendGetRequest(NEXT_FIXTURES_URL));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (JsonNode fixtureNode: fixturesNode) {
            Fixture fixture = new Fixture(
                    fixtureNode.get("team_h").asInt(),
                    fixtureNode.get("team_a").asInt()
            );
            fixtureDao.save(fixture);
        }

        return fixtureDao;
    }
}
