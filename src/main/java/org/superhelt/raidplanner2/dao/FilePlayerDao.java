package org.superhelt.raidplanner2.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.om.Player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FilePlayerDao implements PlayerDao {

    private static final Logger log = LoggerFactory.getLogger(FilePlayerDao.class);

    private final static Path jsonFile = Paths.get("players.json");
    private final static List<Player> players = new ArrayList<>();

    static {
        try {
            readPlayersFromFile();
        } catch (Exception e) {
            log.error("Unable to read {}", jsonFile, e);
        }
    }

    private static void readPlayersFromFile() throws IOException {
        if(Files.exists(jsonFile)) {
            String json = readFile(jsonFile);

            ObjectMapper mapper = new ObjectMapper();
            Player[] savedPlayers = mapper.readerFor(Player[].class).readValue(json);

            players.addAll(Arrays.asList(savedPlayers));
            log.info("Read {} players from {}", players.size(), jsonFile);
        } else {
            log.info("Found no file to read ({})", jsonFile);
        }
    }

    private static void writePlayersToFile() {
        try {
            players.sort(Comparator.comparing(Player::getId));

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(players);

            Files.deleteIfExists(jsonFile);
            Files.write(jsonFile, json.getBytes());
        } catch(Exception e) {
            log.error("Unable to write file {}", jsonFile, e);
        }
    }

    private static String readFile(Path playerJson) throws IOException {
        return String.join("", Files.readAllLines(playerJson));
    }

    @Override
    public List<Player> get() {
        return players;
    }

    @Override
    public Optional<Player> get(int id) {
        return players.stream().filter(p->p.getId()==id).findFirst();
    }

    @Override
    public void update(Player player) {
        players.removeIf(p->p.getId()==player.getId());
        players.add(player);
        writePlayersToFile();
    }

    @Override
    public void add(Player player) {
        players.add(player);
        writePlayersToFile();
    }

    @Override
    public void delete(int id) {
        players.removeIf(p->p.getId()==id);
        writePlayersToFile();
    }
}
