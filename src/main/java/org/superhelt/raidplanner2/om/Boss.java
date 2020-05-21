package org.superhelt.raidplanner2.om;

public class Boss {

    private final int id;
    private final String name;

    public Boss(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
