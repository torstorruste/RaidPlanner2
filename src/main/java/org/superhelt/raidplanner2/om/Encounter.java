package org.superhelt.raidplanner2.om;

import java.util.List;

public class Encounter {

    private final Boss boss;
    private final List<EncounterCharacter> players;

    public Encounter(Boss boss, List<EncounterCharacter> players) {
        this.boss = boss;
        this.players = players;
    }
}
