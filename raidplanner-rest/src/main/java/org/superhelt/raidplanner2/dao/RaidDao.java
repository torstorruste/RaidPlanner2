package org.superhelt.raidplanner2.dao;

import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.om.Raid;

import java.util.List;
import java.util.Optional;

public interface RaidDao {

    List<Raid> getAll();

    Optional<Raid> get(int id);

    void add(Raid raid);

    void update(Raid raid);
}
