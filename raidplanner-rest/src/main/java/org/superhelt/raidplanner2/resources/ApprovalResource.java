package org.superhelt.raidplanner2.resources;

import org.superhelt.raidplanner2.om.Approval;
import org.superhelt.raidplanner2.service.ApprovalService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("approvals")
@Produces(MediaType.APPLICATION_JSON)
public class ApprovalResource {

    private final ApprovalService service;

    @Inject
    public ApprovalResource(ApprovalService service) {
        this.service = service;
    }

    @GET
    public Response getAllApprovals() {
        List<Approval> result = service.getAll();
        return Response.ok(result).build();
    }

}
