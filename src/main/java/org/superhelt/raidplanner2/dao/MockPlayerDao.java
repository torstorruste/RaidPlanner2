package org.superhelt.raidplanner2.dao;

import org.jvnet.hk2.annotations.Service;
import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.CharacterClass;
import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.om.Role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MockPlayerDao implements PlayerDao {

    private static final List<Player> players = new ArrayList<>();

    static {
        players.add(new Player("Zikura", Collections.singletonList(new Character("Zikura", CharacterClass.Druid, Collections.singletonList(Role.Ranged)))));
    }

    public MockPlayerDao() {
    }

    public List<Player> getPlayers() {
        return players;
    }
}
