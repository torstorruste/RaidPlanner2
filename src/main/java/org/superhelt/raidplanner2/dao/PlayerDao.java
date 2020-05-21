package org.superhelt.raidplanner2.dao;

import org.jvnet.hk2.annotations.Service;
import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.CharacterClass;
import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.om.Role;

import javax.inject.Singleton;
import java.util.*;

@Singleton
@Service
public class PlayerDao {

    private static List<Player> players = new ArrayList<>();

    static {
        List<Character> characters = new ArrayList<>();
        characters.add(new Character(1, "Zikura", CharacterClass.Druid, Collections.singletonList(Role.Ranged)));
        Player zikura = new Player(1, "Zikura", characters);
        players.add(zikura);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Optional<Player> getPlayer(int id) {
        return players.stream().filter(p->p.getId()==id).findFirst();
    }

    public void updatePlayer(Player newPlayer) {
        players.removeIf(p->p.getId()==newPlayer.getId());
        players.add(newPlayer);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }
}
