package org.superhelt.raidplanner2.resources;

import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.*;
import org.superhelt.raidplanner2.service.ApprovalService;
import org.superhelt.raidplanner2.service.InstanceService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/instances/{instanceId}/bosses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BossResource {

    private final InstanceService instanceService;
    private final ApprovalService approvalService;

    @Inject
    public BossResource(InstanceService instanceService, ApprovalService approvalService) {
        this.instanceService = instanceService;
        this.approvalService = approvalService;
    }

    @GET
    public Response getBosses(@PathParam("instanceId") int instanceId) {
        Instance instance = instanceService.getInstance(instanceId);

        return Response.ok(instance.getBosses()).build();
    }

    @POST
    public Response addBoss(@PathParam("instanceId") int instanceId, Boss boss) {
        Instance instance = instanceService.getInstance(instanceId);

        Boss savedBoss = instanceService.addBoss(instance, boss);
        return Response.ok(savedBoss).build();
    }

    @PUT
    @Path("/{bossId}")
    public Response updateBoss(@PathParam("instanceId") int instanceId, @PathParam("bossId") int bossId, Boss boss) {
        if(bossId!=boss.getId()) {
            return Response.status(400, "ID Mismatch between body and url").build();
        }

        Instance instance = instanceService.getInstance(instanceId);

        instanceService.updateBoss(instance, boss);
        return Response.ok(boss).build();
    }

    @DELETE
    @Path("/{bossId}")
    public Response deleteBoss(@PathParam("instanceId") int instanceId, @PathParam("bossId") int bossId, Boss boss) {
        Instance instance = instanceService.getInstance(instanceId);

        instanceService.deleteBoss(instance, bossId);
        return Response.ok().build();
    }

    @GET
    @Path("{bossId}/approvals")
    public Response getApprovals(@PathParam("instanceId") int instanceId, @PathParam("bossId") int bossId) {
        List<Approval> approvals = approvalService.getByBoss(bossId);
        return Response.ok(transform(approvals)).build();
    }

    private List<Character> transform(List<Approval> approvals) {
        return approvals.stream().map(Approval::getCharacter).collect(Collectors.toList());
    }

    @POST
    @Path("{bossId}/approvals/{playerId}/{characterId}")
    public Response addApproval(@PathParam("playerId") int playerId, @PathParam("characterId") int characterId,
                                @PathParam("instanceId") int instanceId, @PathParam("bossId") int bossId) {
        Approval approval = approvalService.addApproval(playerId, characterId, instanceId, bossId);
        return Response.ok(approval).build();
    }
}
