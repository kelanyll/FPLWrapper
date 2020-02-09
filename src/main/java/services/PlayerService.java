package services;

import dao.ClubDAO;
import dao.DAOInitialiser;
import dao.PlayerDAO;
import entities.Player;
import representations.PlayerWithStats;

import java.util.HashMap;
import java.util.Map;

public class PlayerService {
    DAOInitialiser daoInitialiser;

    public PlayerService(DAOInitialiser daoInitialiser) {
        this.daoInitialiser = daoInitialiser;
    }

    public PlayerWithStats getPlayer(String name) {
        final Map<Integer, String> positionIdDict = new HashMap<Integer, String>() {{
            put(1, "goalkeeper");
            put(2, "defender");
            put(3, "midfielder");
            put(4, "forward");
        }};

        // We initialise the DAOs every time a request is made so that the
        // DAOs contain the most recent data. This is necessary until we can
        // persist the content of the DAOs in a database and periodically
        // check the FPL API for up-to-date data.
        PlayerDAO playerDao = daoInitialiser.buildPlayerDao(new PlayerDAO());
        ClubDAO clubDao = daoInitialiser.buildClubDao(new ClubDAO());

        // Should return Dropwizard Exception
        Player playerFromDao = playerDao.getByName(name).get();

        PlayerWithStats player = new PlayerWithStats(
                playerFromDao.getName(),
                clubDao.getByCode(playerFromDao.getClubCode()).get().getName(),
                positionIdDict.get(playerFromDao.getPositionId())
        );

        return player;
    }
}
