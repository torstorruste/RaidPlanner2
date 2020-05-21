package org.superhelt.raidplanner2.resources;

import org.superhelt.raidplanner2.dao.PlayerDao;
import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.service.PlayerService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Path("/players")
public class PlayerResource {

    private final PlayerService playerService;

    @Inject
    public PlayerResource(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayers() {
        List<Player> players = playerService.getPlayers();
        return Response.ok(players).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public Response getPlayer(@PathParam("id") int id) {
        Optional<Player> player = playerService.getPlayer(id);
        if(player.isPresent()) return Response.ok(player.get()).build();
        else return Response.status(404).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlayer(Player player) {
        Player savedPlayer = playerService.addPlayer(player);
        return Response.ok(savedPlayer).build();
    }
}
