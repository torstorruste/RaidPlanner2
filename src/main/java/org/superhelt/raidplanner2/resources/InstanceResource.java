package org.superhelt.raidplanner2.resources;


import org.superhelt.raidplanner2.om.Instance;
import org.superhelt.raidplanner2.service.InstanceService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("instances")
public class InstanceResource {

    private InstanceService service;

    @Inject
    public InstanceResource(InstanceService service) {
        this.service = service;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInstances() {
        List<Instance> instances = service.getInstances();
        return Response.ok(instances).build();
    }
}
