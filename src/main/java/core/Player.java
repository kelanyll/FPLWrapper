package core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Player {
	private String firstName;
	private String secondName;
	private int id;
	@JsonProperty("element_type")
	private int positionId;
	@JsonProperty("team_code")
	private int clubCode;
	private String form;
	@JsonProperty("now_cost")
	private int price;
	@JsonProperty("event_points")
	private int gameweekPoints;
	private int totalPoints;
	private String selectedByPercent;
	private String influence;
	private String creativity;
	private String threat;
	private String ictIndex;

	// Jackson
	private Player() {}

	public Player(String firstName, String secondName, int id, int positionId, int clubCode, String form, int price,
	              int gameweekPoints, int totalPoints, String selectedByPercent, String influence, String creativity,
	              String threat, String ictIndex) {
		this.firstName = firstName;
		this.secondName = secondName;
		this.id = id;
		this.positionId = positionId;
		this.clubCode = clubCode;
		this.form = form;
		this.price = price;
		this.gameweekPoints = gameweekPoints;
		this.totalPoints = totalPoints;
		this.selectedByPercent = selectedByPercent;
		this.influence = influence;
		this.creativity = creativity;
		this.threat = threat;
		this.ictIndex = ictIndex;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getName() {
		return String.format("%s %s", firstName, secondName);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPositionId() {
		return positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}

	public int getClubCode() {
		return clubCode;
	}

	public void setClubCode(int clubCode) {
		this.clubCode = clubCode;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getGameweekPoints() {
		return gameweekPoints;
	}

	public void setGameweekPoints(int gameweekPoints) {
		this.gameweekPoints = gameweekPoints;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

	public String getSelectedByPercent() {
		return selectedByPercent;
	}

	public void setSelectedByPercent(String selectedByPercent) {
		this.selectedByPercent = selectedByPercent;
	}

	public String getInfluence() {
		return influence;
	}

	public void setInfluence(String influence) {
		this.influence = influence;
	}

	public String getCreativity() {
		return creativity;
	}

	public void setCreativity(String creativity) {
		this.creativity = creativity;
	}

	public String getThreat() {
		return threat;
	}

	public void setThreat(String threat) {
		this.threat = threat;
	}

	public String getIctIndex() {
		return ictIndex;
	}

	public void setIctIndex(String ictIndex) {
		this.ictIndex = ictIndex;
	}
}