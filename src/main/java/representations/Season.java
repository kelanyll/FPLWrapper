package representations;

public class Season {
	// Jackson
	private Season() {
	}

	// For testing
	public Season(String season_name, int start_cost, int end_cost, int total_points, int minutes, int goals_scored,
	              int assists, int clean_sheets, int goals_conceded, int own_goals, int penalties_saved,
	              int penalties_missed, int yellow_cards, int red_cards, int saves, int bonus, int bps,
	              String influence, String creativity, String threat, String ict_index) {
		this.season_name = season_name;
		this.start_cost = start_cost;
		this.end_cost = end_cost;
		this.total_points = total_points;
		this.minutes = minutes;
		this.goals_scored = goals_scored;
		this.assists = assists;
		this.clean_sheets = clean_sheets;
		this.goals_conceded = goals_conceded;
		this.own_goals = own_goals;
		this.penalties_saved = penalties_saved;
		this.penalties_missed = penalties_missed;
		this.yellow_cards = yellow_cards;
		this.red_cards = red_cards;
		this.saves = saves;
		this.bonus = bonus;
		this.bps = bps;
		this.influence = influence;
		this.creativity = creativity;
		this.threat = threat;
		this.ict_index = ict_index;
	}

	public String season_name;

	public int start_cost;

	public int end_cost;

	public int total_points;

	public int minutes;

	public int goals_scored;

	public int assists;

	public int clean_sheets;

	public int goals_conceded;

	public int own_goals;

	public int penalties_saved;

	public int penalties_missed;

	public int yellow_cards;

	public int red_cards;

	public int saves;

	public int bonus;

	public int bps;

	public String influence;

	public String creativity;

	public String threat;

	public String ict_index;
}
