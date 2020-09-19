package org.superhelt.raidplanner2.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.om.Raid;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class RaidService {

    private static final Logger log = LoggerFactory.getLogger(RaidService.class);

    private static final String REST_URL = "http://localhost:8080/";

    public List<Raid> getRaids() {
        Client client = ClientBuilder.newClient();
        log.info("GET {}raids", REST_URL);
        Raid[] raids = client.target(REST_URL).path("raids").request(MediaType.APPLICATION_JSON).get(Raid[].class);

        List<Raid> result = Arrays.asList(raids);
        result.sort(Comparator.comparing(Raid::getDate).reversed());
        return result;
    }

    public Raid addRaid(Raid raid) {
        Client client = ClientBuilder.newClient();
        log.info("POST {}raids", REST_URL);
        return client.target(REST_URL).path("raids").request(MediaType.APPLICATION_JSON).post(Entity.entity(raid, MediaType.APPLICATION_JSON), Raid.class);
    }

    public Player signup(Raid raid, Player player) {
        Client client = ClientBuilder.newClient();
        log.info("POST {}raids/{}/signups", REST_URL, raid.getId());
        return client.target(REST_URL).path(String.format("raids/%d/signups", raid.getId()))
                .request(MediaType.APPLICATION_JSON).post(Entity.entity(player, MediaType.APPLICATION_JSON), Player.class);
    }

    public void unsign(Raid raid, Player player) {
        Client client = ClientBuilder.newClient();
        log.info("DELETE {}raids/{}/signups/{}", REST_URL, raid.getId(), player.getId());
        client.target(REST_URL).path(String.format("raids/%d/signups/%d", raid.getId(), player.getId()))
                .request(MediaType.APPLICATION_JSON).delete();
    }
}
