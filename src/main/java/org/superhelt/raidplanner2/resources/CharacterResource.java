package org.superhelt.raidplanner2.resources;

import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.service.PlayerService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/players/{playerId}/characters")
public class CharacterResource {

    @Inject
    private PlayerService playerService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCharacters(@PathParam("playerId") int playerId) {
        Optional<Player> player = playerService.getPlayer(playerId);

        if (player.isPresent()) {
            return Response.ok(player.get().getCharacters()).build();
        } else {
            return Response.status(404).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCheracter(@PathParam("playerId") int playerId, Character character) {
        Optional<Player> player = playerService.getPlayer(playerId);
        if(!player.isPresent()) {
            return Response.status(404).build();
        }

        Character savedCharacter = playerService.addCharacter(player.get(), character);
        return Response.ok(savedCharacter).build();
    }

    @PUT
    @Path("/{characterId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCharacter(@PathParam("playerId") int playerId, @PathParam("characterId") int characterId, Character character) {
        if (character.getId() != characterId) {
            return Response.status(400, "ID mismatch").build();
        }

        Optional<Player> player = playerService.getPlayer(playerId);

        if (!player.isPresent()) {
            return Response.status(404).build();
        }

        playerService.updateCharacter(player.get(), character);
        return Response.ok(character).build();
    }

    @DELETE
    @Path("/{characterId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCharacter(@PathParam("playerId") int playerId, @PathParam("characterId") int characterId) {
        Optional<Player> player = playerService.getPlayer(playerId);

        if (!player.isPresent()) {
            return Response.status(404).build();
        }

        playerService.deleteCharacter(player.get(), characterId);
        return Response.ok().build();
    }
}
