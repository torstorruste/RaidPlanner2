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
public class PlayerDaoMock {

    private static List<Player> players = new ArrayList<>();

    static {
        players.add(new Player(1, "Zikura", Collections.singletonList(new Character("Zikura", CharacterClass.Druid, Collections.singletonList(Role.Ranged)))));
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Optional<Player> getPlayer(int id) {
        return players.stream().filter(p->p.getId()==id).findFirst();
    }

    public Player addPlayer(Player player) {
        Player newPlayer = new Player(findId(), player.getName(), new ArrayList<>());
        players.add(newPlayer);
        return newPlayer;
    }

    private int findId() {
        return players.stream().mapToInt(Player::getId).max().getAsInt()+1;
    }
}
