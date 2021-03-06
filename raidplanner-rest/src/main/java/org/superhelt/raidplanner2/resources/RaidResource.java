package org.superhelt.raidplanner2.resources;

import org.superhelt.raidplanner2.ServerException;
import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.om.Raid;
import org.superhelt.raidplanner2.service.PlayerService;
import org.superhelt.raidplanner2.service.RaidService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Path("raids")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RaidResource {

    private final RaidService raidService;
    private final PlayerService playerService;

    @Inject
    public RaidResource(RaidService raidService, PlayerService playerService) {
        this.raidService = raidService;
        this.playerService = playerService;
    }

    @GET
    public Response getRaids() {
        List<Raid> raids = raidService.getRaids();
        return Response.ok(raids).build();
    }

    @GET
    @Path("/latest")
    public Response getLatestRaid() {
        List<Raid> raids = raidService.getRaids();

        Raid latest = raids.stream()
                .filter(Raid::isFinalized)
                .max(Comparator.comparing(Raid::getDate))
                .orElseThrow(() -> new ServerException(404, "No raid is finalized"));

        return Response.ok(latest).build();
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

    @PUT
    @Path("/{raidId}")
    public Response updateRaid(@PathParam("raidId") int raidId, Raid raid) {
        if (raid.getId() != raidId) {
            throw new ServerException(400, "Ids do not match");
        }

        Raid updatedRaid = raidService.updateRaid(raid);
        return Response.ok(updatedRaid).build();
    }

    @DELETE
    @Path("/{raidId}")
    public Response deleteRaid(@PathParam("raidId") int raidId) {
        Raid raid = raidService.getRaid(raidId);
        raidService.deleteRaid(raid);
        return Response.ok().build();
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
}
