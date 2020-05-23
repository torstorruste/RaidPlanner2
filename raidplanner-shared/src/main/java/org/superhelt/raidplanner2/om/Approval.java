package org.superhelt.raidplanner2.om;

public class Approval {

    private Character character;
    private Boss boss;

    public Approval() {
    }

    public Approval(Character character, Boss boss) {
        this.character = character;
        this.boss = boss;
    }

    public Character getCharacter() {
        return character;
    }

    public Boss getBoss() {
        return boss;
    }
}
