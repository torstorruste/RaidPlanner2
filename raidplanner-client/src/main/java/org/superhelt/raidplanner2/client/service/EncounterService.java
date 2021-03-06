package org.superhelt.raidplanner2.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

public class EncounterService {

    private static final Logger log = LoggerFactory.getLogger(EncounterService.class);

    private final String baseUrl;

    public EncounterService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Encounter addEncounter(Raid raid, Boss boss) {
        Client client = ClientBuilder.newClient();
        String path = String.format("raids/%d/encounters", raid.getId());
        log.info("POST {}/{}", baseUrl, path);
        return client.target(baseUrl).path(path).request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(boss, MediaType.APPLICATION_JSON_TYPE), Encounter.class);
    }

    public void deleteEncounter(Raid raid, Encounter encounter) {
        Client client = ClientBuilder.newClient();
        String path = String.format("raids/%d/encounters/%d", raid.getId(), encounter.getId());
        log.info("DELETE {}/{}", baseUrl, path);

        client.target(baseUrl).path(path).request(MediaType.APPLICATION_JSON).delete();
    }

    public void addCharacter(Raid raid, Encounter encounter, EncounterCharacter encounterCharacter) {
        Client client = ClientBuilder.newClient();
        String path = String.format("raids/%d/encounters/%d/characters", raid.getId(), encounter.getId());
        log.info("POST {}/{}", baseUrl, path);
        client.target(baseUrl).path(path).request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(encounterCharacter, MediaType.APPLICATION_JSON_TYPE));
    }

    public void deleteCharacter(Raid raid, Encounter encounter, Character character) {
        Client client = ClientBuilder.newClient();
        String path = String.format("raids/%d/encounters/%d/characters/%d", raid.getId(), encounter.getId(), character.getId());
        log.info("DELETE {}/{}", baseUrl, path);
        client.target(baseUrl).path(path).request(MediaType.APPLICATION_JSON).delete();
    }
}
