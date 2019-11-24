package entities;

public class Player {
    private String firstName;
    private String secondName;
    private int playerId;
    private int positionId;
    private int clubCode;

    public Player(String firstName, String secondName, int playerId, int positionId, int clubCode) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.playerId = playerId;
        this.positionId = positionId;
        this.clubCode = clubCode;
    }

    public String getName() { return firstName + ' ' + secondName; }
    public int getPlayerId() { return playerId; }
    public int getPositionId() { return positionId; }
    public int getClubCode() { return clubCode; }
}