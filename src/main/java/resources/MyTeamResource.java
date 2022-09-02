package resources;

import db.ClubDAO;
import db.PlayerDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import api.MyPlayer;
import core.MyTeamService;
import client.FplUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class MyTeamResource {
	private final MyTeamService myTeamService;

	public MyTeamResource(ClubDAO clubDao, PlayerDAO playerDAO, FplUtil fplUtil) {
		this.myTeamService = new MyTeamService(clubDao, playerDAO, fplUtil);
	}

	@POST
	@ApiOperation(value = "Gets your live FPL team.")
	public List<MyPlayer> getMyTeam(
		@FormParam("email") String email,
		@FormParam("password") String password
	) {
		return myTeamService.getMyTeam(email, password);
	}
}
