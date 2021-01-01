package core;

import java.util.List;
import java.util.Optional;

/**
 * This class stores the fixtures of the current gameweek
 */
public class Fixtures {
	private List<Fixture> fixtures;

	public Fixtures(List<Fixture> fixtures) {
		this.fixtures = fixtures;
	}

	public Optional<Fixture> getByClubId(int clubId) {
		final Fixture fixture = Util.findInList(
			fixtures,
			possibleFixture ->
				possibleFixture.getHomeTeamId() == clubId || possibleFixture.getAwayTeamId() == clubId
		);

		return Optional.ofNullable(fixture);
	}

	public List<Fixture> getFixtures() {
		return fixtures;
	}

	public void setFixtures(List<Fixture> fixtures) {
		this.fixtures = fixtures;
	}
}
