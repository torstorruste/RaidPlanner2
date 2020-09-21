package org.superhelt.raidplanner2.dao;

import org.jvnet.hk2.annotations.Contract;
import org.superhelt.raidplanner2.om.Player;

import java.util.List;
import java.util.Optional;

@Contract
public interface PlayerDao {

    List<Player> get();

    Optional<Player> get(int id);

    Player update(Player player);

    void add(Player player);

    void delete(int id);
}
