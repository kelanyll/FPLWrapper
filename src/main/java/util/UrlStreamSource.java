package util;

import java.net.HttpURLConnection;

public interface UrlStreamSource {
    String sendGetRequest(String url_str);
    HttpURLConnection sendPostRequest(String url_str, String reqBody);
}
