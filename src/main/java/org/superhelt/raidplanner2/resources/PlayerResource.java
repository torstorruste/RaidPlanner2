package org.superhelt.raidplanner2.resources;

import org.superhelt.raidplanner2.dao.MockPlayerDao;
import org.superhelt.raidplanner2.dao.PlayerDao;
import org.superhelt.raidplanner2.om.Player;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/players")
public class PlayerResource {

    private static PlayerDao dao = new MockPlayerDao();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayers() {
        List<Player> players = dao.getPlayers();
        return Response.ok(players).build();
    }
}
