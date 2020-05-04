package org.superhelt.raidplanner2.om;

public class EncounterCharacter {

    private final Player player;
    private final Character character;
    private final Role role;

    public EncounterCharacter(Player player, Character character, Role role) {
        this.player = player;
        this.character = character;
        this.role = role;
    }
}
