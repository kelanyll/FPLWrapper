package dropwizard;

import com.codahale.metrics.health.HealthCheck;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FplHealthCheck extends HealthCheck {
	private HttpClient httpClient;

	public FplHealthCheck(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	@Override
	protected Result check() throws Exception {
		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create("https://fantasy.premierleague.com/"))
			.build();
		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		if (response.statusCode() != 200) {
			return Result.unhealthy("Request to Fantasy Premier League home page returns a " + response.statusCode());
		}

		return Result.healthy();
	}
}
