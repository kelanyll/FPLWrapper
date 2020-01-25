package services;

import dao.ClubDAO;
import dao.FixtureDAO;
import dao.PlayerDAO;
import dropwizard.DropwizardException;
import org.junit.Test;

public class MyTeamServiceTests {
    @Test(expected = DropwizardException.class)
    public void getMyTeam_BadEmailPassword_ExceptionThrown() {
        PlayerDAO playerDAO = new PlayerDAO();
        ClubDAO clubDAO = new ClubDAO();
        FixtureDAO fixtureDAO = new FixtureDAO();
        MyTeamService myTeamService = new MyTeamService(
                playerDAO,
                clubDAO,
                fixtureDAO
        );

        // This call of the service should return a DropwizardException.
        myTeamService.getMyTeam("dfdf", "dfd");
    }
}
