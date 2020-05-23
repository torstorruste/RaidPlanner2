package org.superhelt.raidplanner2.resources;

import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.*;
import org.superhelt.raidplanner2.service.ApprovalService;
import org.superhelt.raidplanner2.service.PlayerService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/players/{playerId}/characters")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CharacterResource {

    private final PlayerService playerService;
    private final ApprovalService approvalService;

    @Inject
    public CharacterResource(PlayerService playerService, ApprovalService approvalService) {
        this.playerService = playerService;
        this.approvalService = approvalService;
    }

    @GET
    public Response getCharacters(@PathParam("playerId") int playerId) {
        Player player = playerService.getPlayer(playerId);

        return Response.ok(player.getCharacters()).build();
    }

    @POST
    public Response addCheracter(@PathParam("playerId") int playerId, Character character) {
        Player player = playerService.getPlayer(playerId);

        Character savedCharacter = playerService.addCharacter(player, character);
        return Response.ok(savedCharacter).build();
    }

    @PUT
    @Path("/{characterId}")
    public Response updateCharacter(@PathParam("playerId") int playerId, @PathParam("characterId") int characterId, Character character) {
        if (character.getId() != characterId) {
            return Response.status(400, "ID mismatch between body and url").build();
        }

        Player player = playerService.getPlayer(playerId);

        playerService.updateCharacter(player, character);
        return Response.ok(character).build();
    }

    @DELETE
    @Path("/{characterId}")
    public Response deleteCharacter(@PathParam("playerId") int playerId, @PathParam("characterId") int characterId) {
        Player player = playerService.getPlayer(playerId);

        playerService.deleteCharacter(player, characterId);
        return Response.ok().build();
    }

    @GET
    @Path("{characterId}/approvals")
    public Response getApprovals(@PathParam("playerId") int playerId, @PathParam("characterId") int characterId) {
        List<Approval> approvals = approvalService.getByCharacter(characterId);

        return Response.ok(transform(approvals)).build();
    }

    private List<Boss> transform(List<Approval> approvals) {
        return approvals.stream().map(Approval::getBoss).collect(Collectors.toList());
    }

    @POST
    @Path("{characterId}/approvals/{instanceId}/{bossId}")
    public Response addApproval(@PathParam("playerId") int playerId, @PathParam("characterId") int characterId,
                                @PathParam("instanceId") int instanceId, @PathParam("bossId") int bossId) {
        Approval approval = approvalService.addApproval(playerId, characterId, instanceId, bossId);
        return Response.ok(approval).build();
    }
}
