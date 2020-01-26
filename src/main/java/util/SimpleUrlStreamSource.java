package util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class SimpleUrlStreamSource implements UrlStreamSource {
    private String buildResponse(InputStream inputStream) {
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

    public String sendGetRequest(String url_str) {
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

    public HttpURLConnection sendPostRequest(String url_str, String reqBody) {
        HttpURLConnection con = null;
        try {
            URL url = new URL(url_str);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setDoOutput(true);
            con.setInstanceFollowRedirects(false);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = reqBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }
}
