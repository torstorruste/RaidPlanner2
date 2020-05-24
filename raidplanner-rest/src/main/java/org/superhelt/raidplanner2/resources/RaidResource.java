package org.superhelt.raidplanner2.resources;

import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.om.Raid;
import org.superhelt.raidplanner2.service.RaidService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("raids")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RaidResource {

    private final RaidService service;

    @Inject
    public RaidResource(RaidService service) {
        this.service = service;
    }

    @GET
    public Response getRaids() {
        List<Raid> raids = service.getAll();
        return Response.ok(raids).build();
    }

    @POST
    public Response addRaid(Raid raid) {
        Raid savedRaid = service.addRaid(raid);
        return Response.ok(savedRaid).build();
    }

    @POST
    @Path("/{raidId}/signups")
    public Response signup(@PathParam("raidId") int raidId, Player player) {
        Raid raid = service.get(raidId);
        Player signupPlayer = service.signup(raid, player);
        return Response.ok(signupPlayer).build();
    }
}
