package org.superhelt.raidplanner2.om;

import java.util.List;

public class Encounter {

    private int id;
    private int bossId;
    private List<EncounterCharacter> characters;

    public Encounter() {
    }

    public Encounter(int id, int bossId, List<EncounterCharacter> characters) {
        this.id = id;
        this.bossId = bossId;
        this.characters = characters;
    }

    public int getId() {
        return id;
    }

    public int getBossId() {
        return bossId;
    }

    public List<EncounterCharacter> getCharacters() {
        return characters;
    }
}
