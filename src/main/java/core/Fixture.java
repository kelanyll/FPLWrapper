package core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Fixture {
	@JsonProperty("team_h")
	private int homeTeamId;
	@JsonProperty("team_a")
	private int awayTeamId;

	// Jackson
	private Fixture() {}

	public Fixture(int homeTeamId, int awayTeamId) {
		this.homeTeamId = homeTeamId;
		this.awayTeamId = awayTeamId;
	}

	public int getHomeTeamId() {
		return homeTeamId;
	}

	public int getAwayTeamId() {
		return awayTeamId;
	}

	public void setHomeTeamId(int homeTeamId) {
		this.homeTeamId = homeTeamId;
	}

	public void setAwayTeamId(int awayTeamId) {
		this.awayTeamId = awayTeamId;
	}
}