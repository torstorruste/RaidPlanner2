package org.superhelt.raidplanner2.om;

public class Approval {

    private final Boss boss;
    private final Character character;
    private final Role role;

    public Approval(Boss boss, Character character, Role role) {
        this.boss = boss;
        this.character = character;
        this.role = role;
    }
}
