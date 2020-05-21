package org.superhelt.raidplanner2.dao;

import org.jvnet.hk2.annotations.Service;
import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.CharacterClass;
import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.om.Role;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    public List<Player> get() {
        return players;
    }

    public Optional<Player> get(int id) {
        return players.stream().filter(p->p.getId()==id).findFirst();
    }

    public void update(Player player) {
        players.removeIf(p->p.getId()==player.getId());
        players.add(player);
    }

    public void add(Player player) {
        players.add(player);
    }

    public void delete(int id) {
        players.removeIf(p->p.getId()==id);
    }
}
