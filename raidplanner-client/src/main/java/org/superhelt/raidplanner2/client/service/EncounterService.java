package org.superhelt.raidplanner2.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.om.Boss;
import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.Encounter;
import org.superhelt.raidplanner2.om.EncounterCharacter;
import org.superhelt.raidplanner2.om.Raid;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

public class EncounterService {

    private static final Logger log = LoggerFactory.getLogger(EncounterService.class);

    private static final String REST_URL = "http://localhost:8080/";

    public Encounter addEncounter(Raid raid, Boss boss) {
        Client client = ClientBuilder.newClient();
        String path = String.format("raids/%d/encounters", raid.getId());
        log.info("POST {}/{}", REST_URL, path);
        return client.target(REST_URL).path(path).request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(boss, MediaType.APPLICATION_JSON_TYPE), Encounter.class);
    }

    public void addCharacter(Raid raid, Encounter encounter, EncounterCharacter encounterCharacter) {
        Client client = ClientBuilder.newClient();
        String path = String.format("raids/%d/encounters/%d/characters", raid.getId(), encounter.getId());
        log.info("POST {}/{}", REST_URL, path);
        client.target(REST_URL).path(path).request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(encounterCharacter, MediaType.APPLICATION_JSON_TYPE));
    }

    public void deleteCharacter(Raid raid, Encounter encounter, Character character) {
        Client client = ClientBuilder.newClient();
        String path = String.format("raids/%d/encounters/%d/characters/%d", raid.getId(), encounter.getId(), character.getId());
        log.info("DELETE {}/{}", REST_URL, path);
        client.target(REST_URL).path(path).request(MediaType.APPLICATION_JSON)
                .delete();
    }
}
