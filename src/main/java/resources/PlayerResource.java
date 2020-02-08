package resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import services.PlayerService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/player")
@Api("/player")
@Produces(MediaType.TEXT_PLAIN)
public class PlayerResource {
    private final PlayerService playerService;

    public PlayerResource() {
        this.playerService = new PlayerService();
    }

    @GET
    @ApiOperation(value = "Gets stats and relevant information about a player.")
    public String getPlayer(@QueryParam("name") String name) {
        return playerService.getPlayer(name);
    }
}
