package org.superhelt.raidplanner2.dao;

import org.glassfish.jersey.process.internal.RequestScoped;
import org.jvnet.hk2.annotations.Service;
import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.CharacterClass;
import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.om.Role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequestScoped
@Service
public class PlayerDaoMock {

    private List<Player> players = new ArrayList<>();

    public PlayerDaoMock() {
        players.add(new Player("Zikura", Collections.singletonList(new Character("Zikura", CharacterClass.Druid, Collections.singletonList(Role.Ranged)))));
    }

    public List<Player> getPlayers() {
        return players;
    }
}
