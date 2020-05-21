package org.superhelt.raidplanner2.om;

import java.util.List;

public class Encounter {

    private int id;
    private Boss boss;
    private List<EncounterCharacter> players;

    public Encounter() {
    }

    public Encounter(int id, Boss boss, List<EncounterCharacter> players) {
        this.id = id;
        this.boss = boss;
        this.players = players;
    }

    public int getId() {
        return id;
    }

    public Boss getBoss() {
        return boss;
    }

    public List<EncounterCharacter> getPlayers() {
        return players;
    }
}
