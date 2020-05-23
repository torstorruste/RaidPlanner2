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
import java.util.Comparator;
import java.util.List;

public class PlayerService {

    private static final Logger log = LoggerFactory.getLogger(PlayerService.class);

    private static final String REST_URL = "http://localhost:8080/";

    public List<Player> getPlayers() {
        Client client = ClientBuilder.newClient();
        log.info("GET {}players", REST_URL);
        Player[] players = client.target(REST_URL).path("players").request(MediaType.APPLICATION_JSON).get(Player[].class);

        List<Player> result = Arrays.asList(players);
        result.sort(Comparator.comparing(Player::getName));
        return result;
    }

    public Player addPlayer(Player player) {
        Client client = ClientBuilder.newClient();
        log.info("POST {}players", REST_URL);

        return client.target(REST_URL).path("players")
                .request(MediaType.APPLICATION_JSON).post(Entity.entity(player, MediaType.APPLICATION_JSON), Player.class);
    }

    public void deletePlayer(Player player) {
        Client client = ClientBuilder.newClient();
        log.info("DELETE {}players/{}", REST_URL, player.getId());

        client.target(REST_URL).path("players/"+player.getId()).request(MediaType.APPLICATION_JSON).delete();
    }

    public Character addCharacter(Player player, Character character) {
        Client client = ClientBuilder.newClient();
        log.info("POST {}players/{}/characters", REST_URL, player.getId());

        return client.target(REST_URL).path("players").path(String.format("%d/characters", player.getId()))
                .request(MediaType.APPLICATION_JSON).post(Entity.entity(character, MediaType.APPLICATION_JSON), Character.class);
    }

    public void updateCharacter(Player player, Character character) {
        Client client = ClientBuilder.newClient();
        log.info("PUT {}players/{}", REST_URL, determineCharacterUrl(player, character));
        client.target(REST_URL).path("players").path(determineCharacterUrl(player, character))
                .request(MediaType.APPLICATION_JSON).put(Entity.entity(character, MediaType.APPLICATION_JSON));
    }

    public void deleteCharacter(Player player, Character character) {
        Client client = ClientBuilder.newClient();
        log.info("DELETE {}players/{}", REST_URL, determineCharacterUrl(player, character));
        client.target(REST_URL).path("players").path(determineCharacterUrl(player, character))
                .request(MediaType.APPLICATION_JSON).delete();
    }

    private String determineCharacterUrl(Player player, Character character) {
        return String.format("%d/characters/%d", player.getId(), character.getId());
    }
}
