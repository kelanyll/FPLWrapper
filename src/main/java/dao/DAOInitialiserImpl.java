package dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dropwizard.DropwizardException;
import entities.Fixture;
import entities.HttpSingleton;
import util.FplUtilities;
import java.util.List;

public class DAOInitialiserImpl implements DAOInitialiser {
	private static final String BOOTSTRAP_URL = "https://fantasy.premierleague.com/api/bootstrap-static/";

	private HttpSingleton httpSingleton;
	private FplUtilities fplUtilities;
	private ObjectMapper objectMapper;

	public DAOInitialiserImpl(HttpSingleton httpSingleton, FplUtilities fplUtilities,
	                          ObjectMapper objectMapper) {
		this.httpSingleton = httpSingleton;
		this.fplUtilities = fplUtilities;
		this.objectMapper = objectMapper;
	}

	public PlayerDAO buildPlayerDao() {
		return getDAO(BOOTSTRAP_URL, PlayerDAO.class);
	}

	public ClubDAO buildClubDao() {
		return getDAO(BOOTSTRAP_URL, ClubDAO.class);
	}

	private <T extends DAO> T getDAO(String url, Class<T> daoClass) {
		try {
			String response = httpSingleton.sendGetRequest(url);
			return objectMapper.readValue(response, daoClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DropwizardException("Something's gone wrong on our end.");
		}
	}

	public FixtureDAO buildFixtureDao() {
		List<Fixture> fixtures = getFixtures();
		FixtureDAO fixtureDAO = new FixtureDAO(fixtures);
		return fixtureDAO;
	}

	private List<Fixture> getFixtures() {
		try {
			String response = httpSingleton.sendGetRequest("https://fantasy.premierleague.com/api/fixtures/?event="
				+ (fplUtilities.getCurrentGameweekId(fplUtilities.getUserId()) + 1));
			return objectMapper.readValue(response, new TypeReference<List<Fixture>>() { });
		} catch (Exception e) {
			e.printStackTrace();
			throw new DropwizardException("Something's gone wrong on our end.");
		}
	}
}
