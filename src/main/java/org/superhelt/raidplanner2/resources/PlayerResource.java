package org.superhelt.raidplanner2.resources;

import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.service.PlayerService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/players")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PlayerResource {

    private final PlayerService service;

    @Inject
    public PlayerResource(PlayerService service) {
        this.service = service;
    }

    @GET
    public Response getPlayers() {
        List<Player> players = service.getPlayers();
        return Response.ok(players).build();
    }

    @GET
    @Path("/{id}")
    public Response getPlayer(@PathParam("id") int id) {
        Optional<Player> player = service.getPlayer(id);
        if(player.isPresent()) return Response.ok(player.get()).build();
        else return Response.status(404).build();
    }

    @POST
    public Response addPlayer(Player player) {
        Player savedPlayer = service.addPlayer(player);
        return Response.ok(savedPlayer).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePlayer(@PathParam("id") int id) {
        service.deletePlayer(id);
        return Response.ok().build();
    }
}
