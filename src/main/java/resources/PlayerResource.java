package resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import db.ClubDAO;
import db.PlayerDAO;
import client.HttpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import api.PlayerWithStats;
import core.PlayerService;

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

	public PlayerResource(ClubDAO clubDao, PlayerDAO playerDAO, HttpUtil httpUtil,
	                      ObjectMapper objectMapper) {
		this.playerService = new PlayerService(clubDao, playerDAO, httpUtil, objectMapper);
	}

	@GET
	@ApiOperation(value = "Gets stats and relevant information about a player.")
	public PlayerWithStats getPlayer(@QueryParam("name") String name) {
		return playerService.getPlayer(name);
	}
}
