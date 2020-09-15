package org.superhelt.raidplanner2.om;

import java.util.List;

public class Encounter {

    private int id;
    private int bossId;
    private List<EncounterCharacter> players;

    public Encounter() {
    }

    public Encounter(int id, int bossId, List<EncounterCharacter> players) {
        this.id = id;
        this.bossId = bossId;
        this.players = players;
    }

    public int getId() {
        return id;
    }

    public int getBossId() {
        return bossId;
    }

    public List<EncounterCharacter> getPlayers() {
        return players;
    }
}
