package org.superhelt.raidplanner2.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.om.Raid;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
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
        result.sort(Comparator.comparing(Raid::getDate));
        return result;

    }
}
