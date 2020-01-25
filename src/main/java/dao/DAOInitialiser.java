package dao;

public interface DAOInitialiser {
    PlayerDAO buildPlayerDao(PlayerDAO playerDao);

    ClubDAO buildClubDao(ClubDAO clubDao);

    FixtureDAO buildFixtureDao(FixtureDAO fixtureDao);
}
