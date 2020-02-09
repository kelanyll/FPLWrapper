package resources;

import dao.DAOInitialiser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import representations.PlayerWithStats;
import services.PlayerService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/player")
@Api("/player")
@Produces(MediaType.APPLICATION_JSON)
public class PlayerResource {
    private final PlayerService playerService;

    public PlayerResource(DAOInitialiser daoInitialiser) {
        this.playerService = new PlayerService(daoInitialiser);
    }

    @GET
    @ApiOperation(value = "Gets stats and relevant information about a player.")
    public PlayerWithStats getPlayer(@QueryParam("name") String name) {
        return playerService.getPlayer(name);
    }
}
