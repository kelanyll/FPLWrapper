package dao;

import entities.Fixture;
import util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class stores the fixtures in the current gameweek
 */
public class FixtureDAO implements DAO<Fixture> {
	private List<Fixture> fixtures;

	// Jackson
	private FixtureDAO() {}

	public FixtureDAO(List<Fixture> fixtures) {
		this.fixtures = fixtures;
	}

	// This function is useless as Fixtures don't have IDs.
	@Override
	public Optional<Fixture> get(int id) {
		// Fixture IDs start at 1
		return Optional.ofNullable(fixtures.get(id - 1));
	}

	public Optional<Fixture> getByClubId(int clubId) {
		final Fixture fixture = Util.findInList(
			fixtures,
			possibleFixture ->
				possibleFixture.getHomeTeamId() == clubId || possibleFixture.getAwayTeamId() == clubId
		);

		return Optional.ofNullable(fixture);
	}

	@Override
	public List<Fixture> getAll() {
		return fixtures;
	}

	@Override
	public void save(Fixture fixture) {
		fixtures.add(fixture);
	}

	public List<Fixture> getFixtures() {
		return fixtures;
	}

	public void setFixtures(List<Fixture> fixtures) {
		this.fixtures = fixtures;
	}
}
