package org.superhelt.raidplanner2.resources;

import org.superhelt.raidplanner2.ServerException;
import org.superhelt.raidplanner2.om.*;
import org.superhelt.raidplanner2.service.InstanceService;
import org.superhelt.raidplanner2.service.PlayerService;
import org.superhelt.raidplanner2.service.RaidService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("raids")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RaidResource {

    private final RaidService raidService;
    private final PlayerService playerService;
    private final InstanceService instanceService;

    @Inject
    public RaidResource(RaidService raidService, PlayerService playerService, InstanceService instanceService) {
        this.raidService = raidService;
        this.playerService = playerService;
        this.instanceService = instanceService;
    }

    @GET
    public Response getRaids() {
        List<Raid> raids = raidService.getRaids();
        return Response.ok(raids).build();
    }

    @GET
    @Path("/{raidId}")
    public Response getRaid(@PathParam("raidId") int raidId) {
        Raid raid = raidService.getRaid(raidId);

        return Response.ok(raid).build();
    }

    @POST
    public Response addRaid(Raid raid) {
        Raid savedRaid = raidService.addRaid(raid);
        return Response.ok(savedRaid).build();
    }

    @POST
    @Path("/{raidId}/signups")
    public Response signup(@PathParam("raidId") int raidId, Player player) {
        Raid raid = raidService.getRaid(raidId);
        Player signupPlayer = raidService.signup(raid, player);
        return Response.ok(signupPlayer).build();
    }

    @DELETE
    @Path("/{raidId}/signups/{playerId}")
    public Response unsign(@PathParam("raidId") int raidId, @PathParam("playerId") int playerId) {
        Raid raid = raidService.getRaid(raidId);
        Player player = playerService.getPlayer(playerId);
        raidService.unsign(raid, player);
        return Response.ok().build();
    }

    @GET
    @Path("/{raidId}/encounters")
    public Response getEncounters(@PathParam("raidId") int raidId) {
        Raid raid = raidService.getRaid(raidId);

        return Response.ok(raid.getEncounters()).build();
    }

    @POST
    @Path("/{raidId}/encounters")
    public Response addEncounter(@PathParam("raidId") int raidId, Boss boss) {
        Raid raid = raidService.getRaid(raidId);

        raidService.addEncounter(raid, boss);

        return Response.ok(raid.getEncounters()).build();
    }

    @GET
    @Path("/{raidId}/encounters/{encounterId}")
    public Response getEncounter(@PathParam("raidId") int raidId, @PathParam("encounterId") int encounterId) {
        Raid raid = raidService.getRaid(raidId);

        Optional<Encounter> encounter = raid.getEncounters().stream()
                .filter(e -> e.getId() == encounterId)
                .findFirst();

        if(encounter.isPresent()) {
            return Response.ok(encounter.get()).build();
        } else {
            throw new ServerException("Encounter %d does not exist for raid %d", encounterId, raidId);
        }
    }

    @DELETE
    @Path("/{raidId}/encounters/{encounterId}")
    public Response deleteEncounter(@PathParam("raidId") int raidId, @PathParam("encounterId") int encounterId) {
        Raid raid = raidService.getRaid(raidId);
        raidService.deleteEncounter(raid, encounterId);

        return Response.ok(raid.getEncounters()).build();
    }
}
