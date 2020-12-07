package services;

import dao.*;
import dropwizard.DropwizardException;
import entities.Club;
import entities.Fixture;
import entities.Player;
import org.junit.Test;
import representations.MyPlayer;
import util.FplUtilities;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;
import java.util.Optional;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class MyTeamServiceTests {
	@Test(expected = DropwizardException.class)
	public void getMyTeam_BadEmailPassword_ExceptionThrown() throws IOException, InterruptedException {
		HttpClient mockedHttpClient = mock(HttpClient.class);
		HttpResponse<String> mockedHttpResponse = mock(HttpResponse.class);
		HttpHeaders mockedHttpHeaders = mock(HttpHeaders.class);

		when(mockedHttpClient.send(any(), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(mockedHttpResponse);
		when(mockedHttpResponse.headers()).thenReturn(mockedHttpHeaders);
		when(mockedHttpHeaders.firstValue(eq("location"))).thenReturn(Optional.of("state=fail"));

		FplUtilities fplUtilities = new FplUtilities(mockedHttpClient);
		DAOInitialiser daoInitialiser = new DAOInitialiserImpl(mockedHttpClient, fplUtilities);
		MyTeamService myTeamService = new MyTeamService(mockedHttpClient, fplUtilities, daoInitialiser);

		// This call of the service should return a DropwizardException.
		myTeamService.getMyTeam("bad_email", "bad_password");
	}

	@Test
	public void getMyTeam_GoodEmailPassword_TeamReturned() throws IOException, InterruptedException {
		HttpClient mockedHttpClient = mock(HttpClient.class);
		HttpResponse mockedHttpResponse = mock(HttpResponse.class);

		when(mockedHttpClient.send(
			argThat(httpRequest -> httpRequest.method() == "GET"),
			eq(HttpResponse.BodyHandlers.ofString())
		)).thenReturn(mockedHttpResponse);
		when(mockedHttpResponse.body()).thenReturn(
			"{\"picks\":[{\"element\":0,\"position\":1,\"is_captain\":true,\"is_vice_captain\":false}]}"
		);

		FplUtilities mockedFplUtilities = mock(FplUtilities.class);
		when(mockedFplUtilities.getUserId()).thenReturn("1997");

		DAOInitialiser mockedDaoInitialiser = mock(DAOInitialiser.class);

		PlayerDAO playerDao = new PlayerDAO();
		Player player = new Player("Eden", "Hazard", 0, 3, 0, "5", 5, 5, 5, "5", "5", "5", "5", "5");
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

		MyTeamService myTeamService = new MyTeamService(mockedHttpClient, mockedFplUtilities, mockedDaoInitialiser);

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
