package health;

import com.codahale.metrics.health.HealthCheck;
import client.HttpUtil;

import java.net.http.HttpResponse;

public class FplHealthCheck extends HealthCheck {
	private HttpUtil httpUtil;

	public FplHealthCheck(HttpUtil httpUtil) {
		this.httpUtil = httpUtil;
	}

	@Override
	protected Result check() throws Exception {
		HttpResponse<String> response = httpUtil.sendGetRequest("https://fantasy.premierleague.com/");
		if (response.statusCode() != 200) {
			return Result.unhealthy("Request to Fantasy Premier League home page returns a " + response.statusCode());
		}

		return Result.healthy();
	}
}
