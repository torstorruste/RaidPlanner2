package org.superhelt.raidplanner2.om;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Boss boss = (Boss) o;
        return id == boss.id &&
                Objects.equals(name, boss.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
