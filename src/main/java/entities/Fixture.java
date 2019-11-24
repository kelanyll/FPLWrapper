package entities;

public class Fixture {
    private int homeTeamId;
    private int awayTeamId;

    public Fixture(int homeTeamId, int awayTeamId) {
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
    }

    public int getHomeTeamId() { return homeTeamId; }
    public int getAwayTeamId() { return awayTeamId; }
}