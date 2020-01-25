package services;

import dao.DAOInitialiser;
import dao.DAOInitialiserImpl;
import dropwizard.DropwizardException;
import org.junit.Test;

public class MyTeamServiceTests {
    @Test(expected = DropwizardException.class)
    public void getMyTeam_BadEmailPassword_ExceptionThrown() {
        DAOInitialiser daoInitialiser = new DAOInitialiserImpl();
        MyTeamService myTeamService = new MyTeamService(daoInitialiser);

        // This call of the service should return a DropwizardException.
        myTeamService.getMyTeam("dfdf", "dfd");
    }
}
