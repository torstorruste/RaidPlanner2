package org.superhelt.raidplanner2.dao;

import org.superhelt.raidplanner2.om.Raid;

import java.util.List;

public interface RaidDao {

    List<Raid> getAll();

    void add(Raid raid);
}
