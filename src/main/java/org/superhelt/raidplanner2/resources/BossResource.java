package org.superhelt.raidplanner2.resources;

import org.superhelt.raidplanner2.om.Boss;
import org.superhelt.raidplanner2.om.Instance;
import org.superhelt.raidplanner2.service.InstanceService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

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
        Optional<Instance> instance = service.getInstance(instanceId);

        if(instance.isPresent()) {
            return Response.ok(instance.get().getBosses()).build();
        } else {
            return Response.status(404).build();
        }
    }

    @POST
    public Response addBoss(@PathParam("instanceId") int instanceId, Boss boss) {
        Optional<Instance> instance = service.getInstance(instanceId);

        if(instance.isPresent()) {
            Instance updatedInstance = service.addBoss(instance.get(), boss);
            return Response.ok(updatedInstance.getBosses()).build();
        } else {
            return Response.status(404).build();
        }
    }

    @PUT
    @Path("/{bossId}")
    public Response updateBoss(@PathParam("instanceId") int instanceId, @PathParam("bossId") int bossId, Boss boss) {
        if(bossId!=boss.getId()) {
            return Response.status(400, "ID Mismatch between body and url").build();
        }

        Optional<Instance> instance = service.getInstance(instanceId);

        if(instance.isPresent()) {
            service.updateBoss(instance.get(), boss);
            return Response.ok(boss).build();
        } else {
            return Response.status(404).build();
        }
    }

    @DELETE
    @Path("/{bossId}")
    public Response deleteBoss(@PathParam("instanceId") int instanceId, @PathParam("bossId") int bossId, Boss boss) {
        Optional<Instance> instance = service.getInstance(instanceId);

        if(instance.isPresent()) {
            service.deleteBoss(instance.get(), bossId);
            return Response.ok().build();
        } else {
            return Response.status(404).build();
        }
    }
}
