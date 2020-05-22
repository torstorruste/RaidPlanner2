package org.superhelt.raidplanner2.resources;

import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.service.PlayerService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/players")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PlayerResource {

    private final PlayerService playerService;

    @Inject
    public PlayerResource(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GET
    public Response getPlayers() {
        List<Player> players = playerService.getPlayers();
        return Response.ok(players).build();
    }

    @GET
    @Path("/{id}")
    public Response getPlayer(@PathParam("id") int id) {
        Player player = playerService.getPlayer(id);
        return Response.ok(player).build();
    }

    @POST
    public Response addPlayer(Player player) {
        if(player.getName()==null || player.getName().isEmpty()) {
            return Response.status(400, "Player needs a name").build();
        }
        Player savedPlayer = playerService.addPlayer(player);
        return Response.ok(savedPlayer).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePlayer(@PathParam("id") int id) {
        playerService.deletePlayer(id);
        return Response.ok().build();
    }
}
