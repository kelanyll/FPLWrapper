package util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dropwizard.DropwizardException;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class FplUtilities {
    private static final String UTF_8_ENC = "utf-8";

    HttpClient httpClient;

    public FplUtilities(HttpClient httpClient) {
        this.httpClient = httpClient;
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
        final String LOGIN_URL = "https://users.premierleague.com/accounts/login/";
        final String LOGIN_REQ_BODY = "login=" + caughtEncode(email, UTF_8_ENC) +
                "&password=" + caughtEncode(pass, UTF_8_ENC) +
                "&app=plfpl-web" +
                "&redirect_uri=https%3A%2F%2Ffantasy.premierleague.com%2F";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(LOGIN_URL))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(LOGIN_REQ_BODY))
                .build();

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Optional<String> optionalRedirectUri = response.headers().firstValue("location");
        String redirectUri = optionalRedirectUri.get();
        if (redirectUri.matches(".*state=success.*")) {
            System.out.println("Successfully authenticated.");
        } else {
            if (redirectUri.matches(".*state=fail.*")) {
                throw new DropwizardException(
                        "Fantasy Premier League's authentication has failed; " +
                                "your email and/or password may be incorrect."
                );
            } else {
                System.out.println(
                        String.format("Authentication failed. Unable to understand response from FPL API.")
                );
            }
        }
    }

    public String getUserId() {
        final String ME_URL = "https://fantasy.premierleague.com/api/me/";
        final String ENTRY_KEY = "\"entry\":";

        HttpResponse<String> response = null;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ME_URL))
                    .build();
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        int entryIndex = response.body().indexOf(ENTRY_KEY) + ENTRY_KEY.length();
        return response.body().substring(entryIndex, response.body().indexOf(",", entryIndex));
    }

    public int getCurrentGameweekId(String userId) {
        final String ENTRY_URL = "https://fantasy.premierleague.com/api/entry/" + userId + "/";

        JsonNode entryNode = null;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ENTRY_URL))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            entryNode = new ObjectMapper().readTree(response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return entryNode.get("current_event").asInt();
    }
}
