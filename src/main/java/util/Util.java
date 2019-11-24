package util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.function.Predicate;

public final class Util {
    private Util() {}

    private static final String UTF_8_ENC = "utf-8";

    private static String buildResponse(InputStream inputStream) {
        StringBuilder respBody = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                respBody.append(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return respBody.toString();
    }

    public static String sendPostRequest(String url_str, String reqBody) {
        String resp = null;
        try {
            URL url = new URL(url_str);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setDoOutput(true);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = reqBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp = buildResponse(con.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resp;
    }

    public static String sendGetRequest(String url_str) {
        String resp = null;
        try {
            URL url = new URL(url_str);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            resp = buildResponse(con.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resp;
    }


    public static String caughtEncode(String string, String enc) {
        String encodedString = null;
        try {
            encodedString = URLEncoder.encode(string, enc);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encodedString;
    }

    private static void initCookies() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);
    }

    public static void authenticate(String email, String pass) {
        final String LOGIN_URL = "https://users.premierleague.com/accounts/login/";
        final String LOGIN_REQ_BODY = "login=" + Util.caughtEncode(email, UTF_8_ENC) +
                "&password=" + Util.caughtEncode(pass, UTF_8_ENC) +
                "&app=plfpl-web" +
                "&redirect_uri=https%3A%2F%2Ffantasy.premierleague.com%2F";

        initCookies();
        sendPostRequest(LOGIN_URL, LOGIN_REQ_BODY);
    }

    public static String getUserId() {
        final String ME_URL = "https://fantasy.premierleague.com/api/me/";
        final String ENTRY_KEY = "\"entry\":";

        String respBody = sendGetRequest(ME_URL);

        int entryIndex = respBody.indexOf(ENTRY_KEY) + ENTRY_KEY.length();
        return respBody.substring(entryIndex, respBody.indexOf(",", entryIndex));
    }

    public static int getCurrentGameweekId(String userId) {
        final String ENTRY_URL = "https://fantasy.premierleague.com/api/entry/" + userId + "/";

        JsonNode entryNode = null;
        try {
            entryNode = new ObjectMapper().readTree(Util.sendGetRequest(ENTRY_URL));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return entryNode.get("current_event").asInt();
    }

    public static <T> T findInList(List<T> list, Predicate<T> condition) {
        return list
                .stream()
                .filter(condition)
                .findAny()
                .orElse(null);
    }
}
