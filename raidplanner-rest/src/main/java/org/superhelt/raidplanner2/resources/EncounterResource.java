package org.superhelt.raidplanner2.resources;

import org.superhelt.raidplanner2.ServerException;
import org.superhelt.raidplanner2.om.Boss;
import org.superhelt.raidplanner2.om.Encounter;
import org.superhelt.raidplanner2.om.EncounterCharacter;
import org.superhelt.raidplanner2.om.Raid;
import org.superhelt.raidplanner2.service.RaidService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("raids/{raidId}/encounters")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EncounterResource {

    private final RaidService raidService;

    @Inject
    public EncounterResource(RaidService raidService) {
        this.raidService = raidService;
    }

    @GET
    public Response getEncounters(@PathParam("raidId") int raidId) {
        Raid raid = raidService.getRaid(raidId);

        return Response.ok(raid.getEncounters()).build();
    }

    @POST
    public Response addEncounter(@PathParam("raidId") int raidId, Boss boss) {
        Raid raid = raidService.getRaid(raidId);

        Encounter encounter = raidService.addEncounter(raid, boss);

        return Response.ok(encounter).build();
    }

    @GET
    @Path("/{encounterId}")
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
    @Path("{encounterId}")
    public Response deleteEncounter(@PathParam("raidId") int raidId, @PathParam("encounterId") int encounterId) {
        Raid raid = raidService.getRaid(raidId);
        raidService.deleteEncounter(raid, encounterId);

        return Response.ok(raid.getEncounters()).build();
    }

    @GET
    @Path("{encounterId}/characters")
    public Response getCharacters(@PathParam("raidId") int raidId, @PathParam("encounterId") int encounterId) {
        Raid raid = raidService.getRaid(raidId);

        List<EncounterCharacter> characters = raid.getEncounters().stream()
                .filter(e -> e.getId() == encounterId)
                .findFirst().get()
                .getCharacters();

        return Response.ok(characters).build();
    }

    @POST
    @Path("{encounterId}/characters")
    public Response addCharacter(@PathParam("raidId") int raidId, @PathParam("encounterId") int encounterId, EncounterCharacter character) {
        Raid raid = raidService.getRaid(raidId);

        raidService.addCharacter(raid, encounterId, character);

        List<EncounterCharacter> characters = raid.getEncounters().stream()
                .filter(e -> e.getId() == encounterId)
                .findFirst().get()
                .getCharacters();

        return Response.ok(characters).build();
    }

    @DELETE
    @Path("{encounterId}/characters/{characterId}")
    public Response deleteCharacter(@PathParam("raidId") int raidId, @PathParam("encounterId") int encounterId, @PathParam("characterId") int characterId) {
        Raid raid = raidService.getRaid(raidId);

        raidService.deleteCharacter(raid, encounterId, characterId);

        List<EncounterCharacter> characters = raid.getEncounters().stream()
                .filter(e -> e.getId() == encounterId)
                .findFirst().get()
                .getCharacters();

        return Response.ok(characters).build();
    }
}
