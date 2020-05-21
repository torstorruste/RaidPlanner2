package org.superhelt.raidplanner2.resources;

import org.superhelt.raidplanner2.om.Boss;
import org.superhelt.raidplanner2.om.Instance;
import org.superhelt.raidplanner2.service.InstanceService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/instances/{instanceId}/bosses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BossResource {

    private final InstanceService service;

    @Inject
    public BossResource(InstanceService service) {
        this.service = service;
    }

    @GET
    public Response getBosses(@PathParam("instanceId") int instanceId) {
        Instance instance = service.getInstance(instanceId);

        return Response.ok(instance.getBosses()).build();
    }

    @POST
    public Response addBoss(@PathParam("instanceId") int instanceId, Boss boss) {
        Instance instance = service.getInstance(instanceId);

        Instance updatedInstance = service.addBoss(instance, boss);
        return Response.ok(updatedInstance.getBosses()).build();
    }

    @PUT
    @Path("/{bossId}")
    public Response updateBoss(@PathParam("instanceId") int instanceId, @PathParam("bossId") int bossId, Boss boss) {
        if(bossId!=boss.getId()) {
            return Response.status(400, "ID Mismatch between body and url").build();
        }

        Instance instance = service.getInstance(instanceId);

        service.updateBoss(instance, boss);
        return Response.ok(boss).build();
    }

    @DELETE
    @Path("/{bossId}")
    public Response deleteBoss(@PathParam("instanceId") int instanceId, @PathParam("bossId") int bossId, Boss boss) {
        Instance instance = service.getInstance(instanceId);

        service.deleteBoss(instance, bossId);
        return Response.ok().build();
    }
}
