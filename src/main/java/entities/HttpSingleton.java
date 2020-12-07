package entities;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class HttpSingleton {
	private static HttpSingleton INSTANCE;
	private HttpClient httpClient;

	private HttpSingleton() {
		this.httpClient = HttpClient.newBuilder()
			.cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL))
			.build();
	}

	public static HttpSingleton getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new HttpSingleton();
		}
		return INSTANCE;
	}

	public String sendGetRequest(String url) throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(url))
			.build();
		return httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
	}
}
