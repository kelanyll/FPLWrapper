package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.*;
import entities.Club;
import entities.Fixture;
import entities.Player;
import representations.MyPlayer;
import util.FplUtilities;
import util.UrlStreamSource;

import java.io.IOException;
import java.util.*;

public class MyTeamService {
    UrlStreamSource urlStreamSource;
    FplUtilities fplUtilities;
    DAOInitialiser daoInitialiser;

    public MyTeamService(UrlStreamSource urlStreamSource, FplUtilities fplUtilities, DAOInitialiser daoInitialiser) {
        this.daoInitialiser = daoInitialiser;
        this.fplUtilities = fplUtilities;
        this.urlStreamSource = urlStreamSource;
    }

    public List<MyPlayer> getMyTeam(String email, String password) {
        fplUtilities.authenticate(email, password);

        // We initialise the DAOs every time a request is made so that the
        // DAOs contain the most recent data. This is necessary until we can
        // persist the content of the DAOs in a database and periodically
        // check the FPL API for up-to-date data.
        PlayerDAO playerDao = daoInitialiser.buildPlayerDao(new PlayerDAO());
        ClubDAO clubDao = daoInitialiser.buildClubDao(new ClubDAO());
        FixtureDAO fixtureDao =  daoInitialiser.buildFixtureDao(new FixtureDAO());

        final String TEAM_URL = "https://fantasy.premierleague.com/api/my-team/" +
                fplUtilities.getUserId() + "/";
        final Map<Integer, String> positionIdDict = new HashMap<Integer, String>() {{
            put(1, "goalkeeper");
            put(2, "defender");
            put(3, "midfielder");
            put(4, "forward");
        }};

        String respBody = urlStreamSource.sendGetRequest(TEAM_URL);

        JsonNode teamNode = null;
        try {
            teamNode = new ObjectMapper().readTree(respBody);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<MyPlayer> myTeam = new ArrayList<>();
        for (JsonNode playerNode: teamNode.get("picks")) {
            // "element" is the key for player ID in the data returned from the FPL my-team endpoint.
            Player player = playerDao.get(playerNode.get("element").asInt()).get();
            Club club = clubDao.getByCode(player.getClubCode()).get();
            Fixture fixture = fixtureDao.getByClubId(club.getId()).get();

            boolean nextFixtureHome = true;
            int nextFixtureOpposingClubId = fixture.getAwayTeamId();
            if (club.getId() == fixture.getAwayTeamId()) {
                nextFixtureHome = false;
                nextFixtureOpposingClubId = fixture.getHomeTeamId();
            }
            Club nextFixtureOpposingClub = clubDao.get(nextFixtureOpposingClubId).get();

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
