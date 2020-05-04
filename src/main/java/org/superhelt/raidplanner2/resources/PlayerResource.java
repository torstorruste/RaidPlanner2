package org.superhelt.raidplanner2.resources;

import org.superhelt.raidplanner2.dao.PlayerDaoMock;
import org.superhelt.raidplanner2.om.Boss;
import org.superhelt.raidplanner2.om.Player;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/players")
public class PlayerResource {

    private final PlayerDaoMock playerDao;

    @Inject
    public PlayerResource(PlayerDaoMock playerDao) {
        this.playerDao = playerDao;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayers() {
        List<Player> players = playerDao.getPlayers();
        return Response.ok(players).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public Response getPlayer(@PathParam("id") int id) {
        Optional<Player> player = playerDao.getPlayer(id);
        if(player.isPresent()) return Response.ok(player.get()).build();
        else return Response.status(404).build();
    }
}
