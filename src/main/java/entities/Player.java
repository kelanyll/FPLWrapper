package entities;

public class Player {
	private String firstName;
	private String secondName;
	private int playerId;
	private int positionId;
	private int clubCode;
	private String form;
	private int price;
	private int gameweekPoints;
	private int totalPoints;
	private String selectedByPercent;
	private String influence;
	private String creativity;
	private String threat;
	private String ictIndex;

	public Player(String firstName, String secondName, int playerId, int positionId, int clubCode, String form,
                  int price, int gameweekPoints, int totalPoints, String selectedByPercent, String influence,
                  String creativity, String threat, String ictIndex) {
		this.firstName = firstName;
		this.secondName = secondName;
		this.playerId = playerId;
		this.positionId = positionId;
		this.clubCode = clubCode;
	}

	public String getName() {
		return firstName + ' ' + secondName;
	}

	public int getPlayerId() {
		return playerId;
	}

	public int getPositionId() {
		return positionId;
	}

	public int getClubCode() {
		return clubCode;
	}

    public String getForm() {
        return form;
    }

    public int getPrice() {
        return price;
    }

    public int getGameweekPoints() {
        return gameweekPoints;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public String getSelectedByPercent() {
        return selectedByPercent;
    }

    public String getInfluence() {
        return influence;
    }

    public String getCreativity() {
        return creativity;
    }

    public String getThreat() {
        return threat;
    }

    public String getIctIndex() {
        return ictIndex;
    }
}