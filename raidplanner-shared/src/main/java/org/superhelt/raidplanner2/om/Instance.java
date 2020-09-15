package org.superhelt.raidplanner2.om;

import java.util.Collections;
import java.util.List;

public class Instance {

    private int id;
    private String name;
    private List<Boss> bosses;

    public Instance() {
        bosses = Collections.emptyList();
    }

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
