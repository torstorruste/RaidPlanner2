package org.superhelt.raidplanner2.om;

public class Boss {

    private int id;
    private String name;

    public Boss() {
    }

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
