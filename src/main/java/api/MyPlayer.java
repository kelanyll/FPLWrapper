package api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MyPlayer {
	private String name;
	private String club;
	private String position;
	private boolean starting;
	private boolean captain;
	private boolean viceCaptain;
	private String nextFixtureOpposingClubName;
	private boolean nextFixtureHome;


	private MyPlayer() {
		// Jackson deserialization
	}

	public MyPlayer(
		String name,
		String club,
		String position,
		boolean starting,
		boolean captain,
		boolean viceCaptain,
		String nextFixtureOpposingClubName,
		boolean nextFixtureHome
	) {
		this.name = name;
		this.club = club;
		this.position = position;
		this.starting = starting;
		this.captain = captain;
		this.viceCaptain = viceCaptain;
		this.nextFixtureOpposingClubName = nextFixtureOpposingClubName;
		this.nextFixtureHome = nextFixtureHome;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	@JsonProperty
	public String getClub() {
		return club;
	}

	@JsonProperty
	public String getPosition() {
		return position;
	}

	@JsonProperty
	public boolean isStarting() {
		return starting;
	}

	@JsonProperty
	public boolean isCaptain() {
		return captain;
	}

	@JsonProperty
	public boolean isViceCaptain() {
		return viceCaptain;
	}

	@JsonProperty
	public String getNextFixtureOpposingClubName() {
		return nextFixtureOpposingClubName;
	}

	@JsonProperty
	public boolean isNextFixtureHome() {
		return nextFixtureHome;
	}
}