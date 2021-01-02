package client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.DropwizardException;
import core.Fixture;
import core.Fixtures;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

public class FplUtil {
	private static final String UTF_8_ENC = "utf-8";

	HttpUtil httpUtil;
	ObjectMapper objectMapper;

	public FplUtil(HttpUtil httpUtil, ObjectMapper objectMapper) {
		this.httpUtil = httpUtil;
		this.objectMapper = objectMapper;
	}

	private String caughtEncode(String string, String enc) {
		String encodedString = null;
		try {
			encodedString = URLEncoder.encode(string, enc);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return encodedString;
	}

	public void authenticate(String email, String pass) {
		try {
			final String LOGIN_URL = "https://users.premierleague.com/accounts/login/";
			final String LOGIN_REQ_BODY = "login=" + caughtEncode(email, UTF_8_ENC) +
				"&password=" + caughtEncode(pass, UTF_8_ENC) +
				"&app=plfpl-web" +
				"&redirect_uri=https%3A%2F%2Ffantasy.premierleague.com%2F";

			HttpResponse<String> response = httpUtil.sendPostRequest(LOGIN_URL, LOGIN_REQ_BODY);

			Optional<String> optionalRedirectUri = response.headers().firstValue("location");
			String redirectUri = optionalRedirectUri.get();
			if (redirectUri.matches(".*state=success.*")) {
				System.out.println("Successfully authenticated.");
			} else {
				throw new DropwizardException(
					"Fantasy Premier League's authentication has failed; " +
						"your email and/or password may be incorrect."
				);
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			throw new DropwizardException("Cannot authenticate with FPL API.");
		}
	}

	public String getUserID() {
		try {
			final String ME_URL = "https://fantasy.premierleague.com/api/me/";
			final String ENTRY_KEY = "\"entry\":";

			HttpResponse<String> response = httpUtil.sendGetRequest(ME_URL);
			JsonNode meNode = objectMapper.readTree(response.body());
			return meNode.get("player").get("entry").asText();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			throw new DropwizardException("Cannot get user ID from FPL API.");
		}
	}

	public int getCurrentGameweekId(String userId) {
		try {
			final String ENTRY_URL = "https://fantasy.premierleague.com/api/entry/" + userId + "/";

			HttpResponse<String> response = httpUtil.sendGetRequest(ENTRY_URL);
			JsonNode entryNode = objectMapper.readTree(response.body());

			return entryNode.get("current_event").asInt();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			throw new DropwizardException("Cannot get current gameweek ID from FPL API.");
		}
	}

	public Fixtures getFixtures() {
		try {
			String response = httpUtil.sendGetRequest("https://fantasy.premierleague.com/api/fixtures/?event="
				+ (getCurrentGameweekId(getUserID()) + 1)).body();
			List<Fixture> fixtures = objectMapper.readValue(response, new TypeReference<List<Fixture>>() {
			});
			return new Fixtures(fixtures);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DropwizardException("Cannot get current fixtures from FPL API.");
		}
	}

	public JsonNode getUserTeam(String userID) {
		try {
			final String TEAM_URL = "https://fantasy.premierleague.com/api/my-team/" +
				userID + "/";
			HttpResponse<String> response = httpUtil.sendGetRequest(TEAM_URL);
			return objectMapper.readTree(response.body());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			throw new DropwizardException(String.format("Cannot get team for user %s from FPL API.", userID));
		}
	}
}
