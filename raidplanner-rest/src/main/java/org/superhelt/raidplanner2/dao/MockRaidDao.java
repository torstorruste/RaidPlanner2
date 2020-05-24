package org.superhelt.raidplanner2.dao;

import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.om.Raid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Optional<Raid> get(int id) {
        return raids.stream().filter(r->r.getId()==id).findFirst();
    }

    @Override
    public void add(Raid raid) {
        raids.add(raid);
    }

    @Override
    public void update(Raid raid) {
        raids.removeIf(r->r.getId()==raid.getId());
        raids.add(raid);
    }
}
