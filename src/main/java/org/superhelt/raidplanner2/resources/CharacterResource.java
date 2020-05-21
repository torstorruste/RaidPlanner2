package org.superhelt.raidplanner2.resources;

import org.superhelt.raidplanner2.dao.PlayerDao;
import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.Player;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/players/{playerId}/characters")
public class CharacterResource {

    @Inject
    private PlayerDao playerDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCharacters(@PathParam("playerId") int playerId) {
        Optional<Player> player = playerDao.getPlayer(playerId);

        if(player.isPresent()) {
            return Response.ok(player.get().getCharacters()).build();
        } else {
            return Response.status(404).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCheracter(@PathParam("playerId") int playerId, Character character) {
        Optional<Player> player = playerDao.getPlayer(playerId);

        if(player.isPresent()) {
            boolean alreadyExists = player.get().getCharacters().stream().anyMatch(c -> c.getName().equals(character.getName()));
            if(alreadyExists) {
                return Response.status(400).build();
            } else {
                player.get().getCharacters().add(character);
                playerDao.updatePlayer(player.get());
                return Response.ok(character).build();
            }
        } else {
            return Response.status(404).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCharacter(@PathParam("playerId") int playerId, Character character) {
        Optional<Player> player = playerDao.getPlayer(playerId);

        if(player.isPresent()) {
            boolean alreadyExists = player.get().getCharacters().stream().anyMatch(c -> c.getName().equals(character.getName()));
            if(alreadyExists) {
                player.get().getCharacters().removeIf(c->c.getName().equals(character.getName()));
                player.get().getCharacters().add(character);
                playerDao.updatePlayer(player.get());
                return Response.ok(character).build();
            } else {
                return Response.status(400).build();
            }
        } else {
            return Response.status(404).build();
        }
    }
}
