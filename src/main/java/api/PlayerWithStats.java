package api;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PlayerWithStats {
	// Jackson
	private PlayerWithStats() {
	}

	// For testing
	public PlayerWithStats(String name, String clubName, String position, List<Gameweek> history,
	                       List<Season> history_past) {
		this.name = name;
		this.clubName = clubName;
		this.position = position;
		this.history = history;
		this.history_past = history_past;
	}

	// For testing
	public PlayerWithStats(List<Gameweek> history, List<Season> history_past) {
		this.history = history;
		this.history_past = history_past;
	}

	private String name;
	private String clubName;
	private String position;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClubName() {
		return clubName;
	}

	public void setClubName(String clubName) {
		this.clubName = clubName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	private List<Gameweek> history;

	public List<Gameweek> getHistory() {
		return history;
	}

	public void setHistory(List<Gameweek> history) {
		this.history = history;
	}

	private List<Season> history_past;

	public List<Season> getHistory_past() {
		return history_past;
	}

	public void setHistory_past(List<Season> history_past) {
		this.history_past = history_past;
	}
}
