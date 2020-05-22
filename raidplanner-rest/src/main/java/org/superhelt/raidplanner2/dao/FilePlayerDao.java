package org.superhelt.raidplanner2.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.om.Player;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FilePlayerDao implements PlayerDao {

    private static final Logger log = LoggerFactory.getLogger(FilePlayerDao.class);

    private final static Path jsonFile = Paths.get("players.json");
    private final static List<Player> players = new ArrayList<>();

    static {
        try {
            List<Player> storedPlayers = FileWriter.readFromFile(jsonFile, Player[].class);
            players.addAll(storedPlayers);
        } catch (Exception e) {
            log.error("Unable to read {}", jsonFile, e);
        }
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
        FileWriter.writeToFile(jsonFile, players);
    }

    @Override
    public void add(Player player) {
        players.add(player);
        FileWriter.writeToFile(jsonFile, players);
    }

    @Override
    public void delete(int id) {
        players.removeIf(p->p.getId()==id);
        FileWriter.writeToFile(jsonFile, players);
    }
}
