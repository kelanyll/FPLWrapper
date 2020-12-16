package dao;

public interface DAOInitialiser {
	PlayerDAO buildPlayerDao();

	ClubDAO buildClubDao();

	FixtureDAO buildFixtureDao();
}
