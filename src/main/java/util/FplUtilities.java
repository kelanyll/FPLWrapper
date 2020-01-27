package util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dropwizard.DropwizardException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;

public class FplUtilities {
    private static final String UTF_8_ENC = "utf-8";

    UrlStreamSource urlStreamSource;

    public FplUtilities(UrlStreamSource urlStreamSource) {
        this.urlStreamSource = urlStreamSource;
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

    private void initCookies() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);
    }

    public void authenticate(String email, String pass) {
        final String LOGIN_URL = "https://users.premierleague.com/accounts/login/";
        final String LOGIN_REQ_BODY = "login=" + caughtEncode(email, UTF_8_ENC) +
                "&password=" + caughtEncode(pass, UTF_8_ENC) +
                "&app=plfpl-web" +
                "&redirect_uri=https%3A%2F%2Ffantasy.premierleague.com%2F";

        initCookies();

        HttpURLConnection con = urlStreamSource.sendPostRequest(LOGIN_URL, LOGIN_REQ_BODY);

        String redirectUri = con.getHeaderField("Location");
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

        String respBody = urlStreamSource.sendGetRequest(ME_URL);

        int entryIndex = respBody.indexOf(ENTRY_KEY) + ENTRY_KEY.length();
        return respBody.substring(entryIndex, respBody.indexOf(",", entryIndex));
    }

    public int getCurrentGameweekId(String userId) {
        final String ENTRY_URL = "https://fantasy.premierleague.com/api/entry/" + userId + "/";

        JsonNode entryNode = null;
        try {
            entryNode = new ObjectMapper().readTree(urlStreamSource.sendGetRequest(ENTRY_URL));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return entryNode.get("current_event").asInt();
    }
}
