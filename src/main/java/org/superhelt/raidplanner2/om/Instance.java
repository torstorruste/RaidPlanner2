package org.superhelt.raidplanner2.om;

import java.util.List;

public class Instance {

    private final int id;
    private final String name;
    private final List<Boss> bosses;

    public Instance(int id, String name, List<Boss> bosses) {
        this.id = id;
        this.name = name;
        this.bosses = bosses;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Boss> getBosses() {
        return bosses;
    }
}
