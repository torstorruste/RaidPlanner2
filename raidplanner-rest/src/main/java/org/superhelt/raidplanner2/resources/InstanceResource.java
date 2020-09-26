package org.superhelt.raidplanner2.resources;


import org.superhelt.raidplanner2.ServerException;
import org.superhelt.raidplanner2.om.Instance;
import org.superhelt.raidplanner2.service.InstanceService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("instances")
@Produces(MediaType.APPLICATION_JSON)
public class InstanceResource {

    private final InstanceService service;

    @Inject
    public InstanceResource(InstanceService service) {
        this.service = service;
    }

    @GET
    public Response getInstances() {
        List<Instance> instances = service.getInstances();
        return Response.ok(instances).build();
    }

    @GET
    @Path("/{id}")
    public Response getInstance(@PathParam("id") int id) {
        Instance instance = service.getInstance(id);

        return Response.ok(instance).build();
    }

    @POST
    public Response addInstance(Instance instance) {
        Instance savedInstance = service.addInstance(instance);
        return Response.ok(savedInstance).build();
    }

    @PUT
    @Path("/{instanceId}")
    public Response updateInstance(@PathParam("instanceId") int instanceId, Instance instance) {
        if(instanceId!=instance.getId()) {
            throw new ServerException(400, "Instance id does not match");
        }
        Instance savedInstance = service.updateInstance(instance);
        return Response.ok(savedInstance).build();
    }

    @DELETE
    @Path("/{instanceId}")
    public Response deleteInstance(@PathParam("instanceId") int id) {
        service.deleteInstance(id);
        return Response.ok().build();
    }
}
