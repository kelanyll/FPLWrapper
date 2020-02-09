package representations;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerWithStats {
    private String name;
    private String club;
    private String position;

    private PlayerWithStats() {
        // Jackson deserialization
    }

    public PlayerWithStats(String name, String club, String position) {
        this.name = name;
        this.club = club;
        this.position = position;
    }

    @JsonProperty
    public String getName() { return name; }

    @JsonProperty
    public String getClub() { return club; }

    @JsonProperty
    public String getPosition() { return position; }
}
