package services;

import dao.DAOInitialiser;
import dao.DAOInitialiserImpl;
import dropwizard.DropwizardException;
import org.junit.Test;
import util.FplUtilities;
import util.SimpleUrlStreamSource;
import util.UrlStreamSource;

public class MyTeamServiceTests {
    @Test(expected = DropwizardException.class)
    public void getMyTeam_BadEmailPassword_ExceptionThrown() {
        UrlStreamSource urlStreamSource = new SimpleUrlStreamSource();
        FplUtilities fplUtilities = new FplUtilities(urlStreamSource);
        DAOInitialiser daoInitialiser = new DAOInitialiserImpl(urlStreamSource, fplUtilities);
        MyTeamService myTeamService = new MyTeamService(urlStreamSource, fplUtilities, daoInitialiser);

        // This call of the service should return a DropwizardException.
        myTeamService.getMyTeam("dfdf", "dfd");
    }
}
