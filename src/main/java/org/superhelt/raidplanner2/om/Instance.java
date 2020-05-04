package org.superhelt.raidplanner2.om;

import java.util.List;

public class Instance {
    private final String name;
    private final List<Boss> bosses;

    public Instance(String name, List<Boss> bosses) {
        this.name = name;
        this.bosses = bosses;
    }
}
