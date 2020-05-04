package org.superhelt.raidplanner2.dao;

import org.jvnet.hk2.annotations.Service;
import org.superhelt.raidplanner2.om.Player;

import java.util.List;

@Service
public interface PlayerDao {

    List<Player> getPlayers();
}
