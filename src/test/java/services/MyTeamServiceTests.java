package services;

import dao.*;
import dropwizard.DropwizardException;
import entities.Club;
import entities.Fixture;
import entities.Player;
import representations.MyPlayer;
import util.FplUtilities;
import util.UrlStreamSource;

import java.net.HttpURLConnection;

import org.junit.Test;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MyTeamServiceTests {
    @Test(expected = DropwizardException.class)
    public void getMyTeam_BadEmailPassword_ExceptionThrown() {
        UrlStreamSource mockedUrlStreamSource = mock(UrlStreamSource.class);
        HttpURLConnection mockedHttpUrlConnection = mock(HttpURLConnection.class);

        when(mockedUrlStreamSource.sendPostRequest(anyString(), anyString())).thenReturn(mockedHttpUrlConnection);
        when(mockedHttpUrlConnection.getHeaderField("Location")).thenReturn("state=fail");

        FplUtilities fplUtilities = new FplUtilities(mockedUrlStreamSource);
        DAOInitialiser daoInitialiser = new DAOInitialiserImpl(mockedUrlStreamSource, fplUtilities);
        MyTeamService myTeamService = new MyTeamService(mockedUrlStreamSource, fplUtilities, daoInitialiser);

        // This call of the service should return a DropwizardException.
        myTeamService.getMyTeam("bad_email", "bad_password");
    }

    @Test
    public void getMyTeam_GoodEmailPassword_TeamReturned() {
        UrlStreamSource mockedUrlStreamSource = mock(UrlStreamSource.class);
        FplUtilities mockedFplUtilities = mock(FplUtilities.class);
        DAOInitialiser mockedDaoInitialiser = mock(DAOInitialiser.class);

        when(mockedUrlStreamSource.sendGetRequest(any())).thenReturn(
                "{\"picks\":[{\"element\":0,\"position\":1,\"is_captain\":true,\"is_vice_captain\":false}]}"
        );

        PlayerDAO playerDao = new PlayerDAO();
        Player player = new Player("Eden", "Hazard", 0, 3, 0);
        playerDao.save(player);
        when(mockedDaoInitialiser.buildPlayerDao(any())).thenReturn(playerDao);

        ClubDAO clubDao = new ClubDAO();
        Club club = new Club("Chelsea", 0, 0);
        Club opposingClub = new Club("Spurs", 1, 1);
        clubDao.save(club);
        clubDao.save(opposingClub);
        when(mockedDaoInitialiser.buildClubDao(any())).thenReturn(clubDao);

        FixtureDAO fixtureDao = new FixtureDAO();
        Fixture fixture = new Fixture(0, 1);
        fixtureDao.save(fixture);
        when(mockedDaoInitialiser.buildFixtureDao(any())).thenReturn(fixtureDao);

        MyTeamService myTeamService = new MyTeamService(mockedUrlStreamSource, mockedFplUtilities, mockedDaoInitialiser);

        MyPlayer expectedMyPlayer = new MyPlayer(
                "Eden Hazard",
                "Chelsea",
                "midfielder",
                true,
                true,
                false,
                "Spurs",
                true
        );

        assertThat(
                myTeamService.getMyTeam("good_email", "good_password").get(0),
                samePropertyValuesAs(expectedMyPlayer)
        );
    }
}
