package org.superhelt.raidplanner2.dao;

import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.Player;

import javax.inject.Singleton;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Singleton
public class FilePlayerDao implements PlayerDao {

    private static final Logger log = LoggerFactory.getLogger(FilePlayerDao.class);

    private final static Path jsonFile = Paths.get("players.json");
    private final static List<Player> players = new ArrayList<>();

    static {
        try {
            List<Player> storedPlayers = FileWriter.readFromFile(jsonFile, Player[].class);
            players.addAll(storedPlayers);
            sortPlayers();
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
    public Player update(Player player) {
        players.removeIf(p->p.getId()==player.getId());
        players.add(player);
        sortPlayers();
        FileWriter.writeToFile(jsonFile, players);
        return player;
    }

    @Override
    public void add(Player player) {
        players.add(player);
        sortPlayers();
        FileWriter.writeToFile(jsonFile, players);
    }

    @Override
    public void delete(int id) {
        players.removeIf(p->p.getId()==id);
        sortPlayers();
        FileWriter.writeToFile(jsonFile, players);
    }

    private static void sortPlayers() {
        players.sort(Comparator.comparing(Player::getName));
        players.forEach(p->p.getCharacters().sort(Comparator.comparing(Character::getName)));
    }
}
