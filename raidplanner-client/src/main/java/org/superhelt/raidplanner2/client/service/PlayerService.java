package org.superhelt.raidplanner2.client.service;

import org.superhelt.raidplanner2.om.Player;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

public class PlayerService {

    public List<Player> getPlayers() {
        Client client = ClientBuilder.newClient();
        Player[] players = client.target("http://localhost:8080/").path("players").request(MediaType.APPLICATION_JSON).get(Player[].class);

        return Arrays.asList(players);
    }
}
