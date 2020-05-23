package org.superhelt.raidplanner2.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.om.Boss;
import org.superhelt.raidplanner2.om.Instance;
import org.superhelt.raidplanner2.om.Player;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class InstanceService {

    private static final Logger log = LoggerFactory.getLogger(InstanceService.class);

    private static final String REST_URL = "http://localhost:8080/";

    public List<Instance> getInstances() {
        Client client = ClientBuilder.newClient();
        log.info("GET {}/instances", REST_URL);
        Instance[] players = client.target(REST_URL).path("instances").request(MediaType.APPLICATION_JSON).get(Instance[].class);

        List<Instance> result = Arrays.asList(players);
        result.sort(Comparator.comparing(Instance::getName));
        return result;
    }

    public void updateBoss(Instance instance, Boss boss) {
        Client client = ClientBuilder.newClient();
        log.info("PUT {}/instances/{}/bosses/{}", REST_URL, instance.getId(), boss.getId());

        client.target(REST_URL).path("instances").path(buildBossUrl(instance, boss)).request(MediaType.APPLICATION_JSON)
        .put(Entity.entity(boss, MediaType.APPLICATION_JSON_TYPE));
    }

    private String buildBossUrl(Instance instance, Boss boss) {
        return String.format("/%d/bosses/%d", instance.getId(), boss.getId());
    }
}
