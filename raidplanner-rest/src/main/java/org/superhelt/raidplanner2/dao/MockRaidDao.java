package org.superhelt.raidplanner2.dao;

import org.superhelt.raidplanner2.om.Raid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MockRaidDao implements RaidDao {

    private static final List<Raid> raids = new ArrayList<>();

    static {
        raids.add(new Raid(1, LocalDate.now(), new ArrayList<>(), new ArrayList<>()));
    }

    @Override
    public List<Raid> getAll() {
        return raids;
    }

    @Override
    public void add(Raid raid) {
        raids.add(raid);
    }
}
