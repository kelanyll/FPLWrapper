package resources;

import dao.ClubDAO;
import dao.FixtureDAO;
import dao.PlayerDAO;
import representations.MyPlayer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import services.MyTeamService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/my-team")
@Api("/my-team")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class MyTeamResource {
    private final MyTeamService myTeamService;

    public MyTeamResource(
            PlayerDAO playerDao,
            ClubDAO clubDao,
            FixtureDAO fixtureDao
    ) {
        this.myTeamService = new MyTeamService(
                playerDao,
                clubDao,
                fixtureDao
        );
    }

    @POST
    @ApiOperation(value = "Gets your live FPL team")
    public List<MyPlayer> getMyTeam(
            @FormParam("email") String email,
            @FormParam("password") String password
    ) {
        return myTeamService.getMyTeam(email, password);
    }
}
