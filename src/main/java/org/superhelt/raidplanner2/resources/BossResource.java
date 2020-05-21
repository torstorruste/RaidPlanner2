package org.superhelt.raidplanner2.resources;

import org.superhelt.raidplanner2.om.Instance;
import org.superhelt.raidplanner2.service.InstanceService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/instances/{instanceId}/bosses")
public class BossResource {

    private final InstanceService service;

    @Inject
    public BossResource(InstanceService service) {
        this.service = service;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBosses(@PathParam("instanceId") int instanceId) {
        Optional<Instance> instance = service.getInstance(instanceId);

        if(instance.isPresent()) {
            return Response.ok(instance.get().getBosses()).build();
        } else {
            return Response.status(404).build();
        }
    }
}
