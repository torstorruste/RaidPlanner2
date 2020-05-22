package org.superhelt.raidplanner2.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.om.Character;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

public class PlayerService {

    private static final Logger log = LoggerFactory.getLogger(PlayerService.class);

    private static final String REST_URL = "http://localhost:8080/";

    public List<Player> getPlayers() {
        Client client = ClientBuilder.newClient();
        log.info("GET {}/players", REST_URL);
        Player[] players = client.target(REST_URL).path("players").request(MediaType.APPLICATION_JSON).get(Player[].class);

        return Arrays.asList(players);
    }

    public void updateCharacter(Player player, Character character) {
        Client client = ClientBuilder.newClient();
        log.info("PUT {}/players/{}", REST_URL, determineCharacterUrl(player, character));
        client.target(REST_URL).path("players").path(determineCharacterUrl(player, character))
                .request(MediaType.APPLICATION_JSON).put(Entity.entity(character, MediaType.APPLICATION_JSON));
    }

    private String determineCharacterUrl(Player player, Character character) {
        return String.format("%d/characters/%d", player.getId(), character.getId());
    }
}
