package resources;

import dao.DAOInitialiser;
import representations.MyPlayer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import services.MyTeamService;
import util.FplUtilities;
import util.UrlStreamSource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/my-team")
@Api("/my-team")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class MyTeamResource {
    private final MyTeamService myTeamService;

    public MyTeamResource(UrlStreamSource urlStreamSource, FplUtilities fplUtilities, DAOInitialiser daoInitialiser) {
        this.myTeamService = new MyTeamService(urlStreamSource, fplUtilities, daoInitialiser);
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
