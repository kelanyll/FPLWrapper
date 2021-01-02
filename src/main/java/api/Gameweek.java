package api;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Gameweek {
	// Jackson
	private Gameweek() {
	}

	// For testing
	public Gameweek(String opponent, int opponent_team, int homeTeamScore, int awayTeamScore, int gameweek,
	                int transfersChange, int own_goals, int bonus, int total_points, int clean_sheets,
	                int goals_conceded, String kickoff_time, int red_cards, String influence, int saves, int assists,
	                int transfers_in, String creativity, int value, int selected, int goals_scored, boolean was_home,
	                int bps, int minutes, int penalties_missed, int yellow_cards, int transfers_out, String threat,
	                String ict_index, int penalties_saved) {
		this.opponent = opponent;
		this.opponent_team = opponent_team;
		this.homeTeamScore = homeTeamScore;
		this.awayTeamScore = awayTeamScore;
		this.gameweek = gameweek;
		this.transfersChange = transfersChange;
		this.own_goals = own_goals;
		this.bonus = bonus;
		this.total_points = total_points;
		this.clean_sheets = clean_sheets;
		this.goals_conceded = goals_conceded;
		this.kickoff_time = kickoff_time;
		this.red_cards = red_cards;
		this.influence = influence;
		this.saves = saves;
		this.assists = assists;
		this.transfers_in = transfers_in;
		this.creativity = creativity;
		this.value = value;
		this.selected = selected;
		this.goals_scored = goals_scored;
		this.was_home = was_home;
		this.bps = bps;
		this.minutes = minutes;
		this.penalties_missed = penalties_missed;
		this.yellow_cards = yellow_cards;
		this.transfers_out = transfers_out;
		this.threat = threat;
		this.ict_index = ict_index;
		this.penalties_saved = penalties_saved;
	}

	// For testing
	public Gameweek(int opponent_team, int homeTeamScore, int awayTeamScore, int gameweek, int transfersChange,
	                int own_goals, int bonus, int total_points, int clean_sheets, int goals_conceded,
	                String kickoff_time, int red_cards, String influence, int saves, int assists, int transfers_in,
	                String creativity, int value, int selected, int goals_scored, boolean was_home, int bps,
	                int minutes, int penalties_missed, int yellow_cards, int transfers_out, String threat,
	                String ict_index, int penalties_saved) {
		this.opponent_team = opponent_team;
		this.homeTeamScore = homeTeamScore;
		this.awayTeamScore = awayTeamScore;
		this.gameweek = gameweek;
		this.transfersChange = transfersChange;
		this.own_goals = own_goals;
		this.bonus = bonus;
		this.total_points = total_points;
		this.clean_sheets = clean_sheets;
		this.goals_conceded = goals_conceded;
		this.kickoff_time = kickoff_time;
		this.red_cards = red_cards;
		this.influence = influence;
		this.saves = saves;
		this.assists = assists;
		this.transfers_in = transfers_in;
		this.creativity = creativity;
		this.value = value;
		this.selected = selected;
		this.goals_scored = goals_scored;
		this.was_home = was_home;
		this.bps = bps;
		this.minutes = minutes;
		this.penalties_missed = penalties_missed;
		this.yellow_cards = yellow_cards;
		this.transfers_out = transfers_out;
		this.threat = threat;
		this.ict_index = ict_index;
		this.penalties_saved = penalties_saved;
	}

	private String opponent;

	public String getOpponent() {
		return opponent;
	}

	public void setOpponent(String opponent) {
		this.opponent = opponent;
	}

	private int opponent_team;

	@JsonIgnore
	public int getOpponent_team() {
		return opponent_team;
	}

	@JsonProperty
	public void setOpponent_team(int opponent_team) {
		this.opponent_team = opponent_team;
	}

	@JsonAlias("team_h_score")
	public int homeTeamScore;

	@JsonAlias("team_a_score")
	public int awayTeamScore;

	@JsonAlias("round")
	public int gameweek;

	@JsonAlias("transfers_balance")
	public int transfersChange;

	public int own_goals;

	public int bonus;

	public int total_points;

	public int clean_sheets;

	public int goals_conceded;

	public String kickoff_time;

	public int red_cards;

	public String influence;

	public int saves;

	public int assists;

	public int transfers_in;

	public String creativity;

	public int value;

	public int selected;

	public int goals_scored;

	public boolean was_home;

	public int bps;

	public int minutes;

	public int penalties_missed;

	public int yellow_cards;

	public int transfers_out;

	public String threat;

	public String ict_index;

	public int penalties_saved;
}
