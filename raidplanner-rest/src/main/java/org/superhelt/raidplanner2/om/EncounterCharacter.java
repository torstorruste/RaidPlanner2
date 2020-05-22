package org.superhelt.raidplanner2.om;

public class EncounterCharacter {

    private Player player;
    private Character character;
    private Role role;

    public EncounterCharacter() {
    }

    public EncounterCharacter(Player player, Character character, Role role) {
        this.player = player;
        this.character = character;
        this.role = role;
    }

    public Player getPlayer() {
        return player;
    }

    public Character getCharacter() {
        return character;
    }

    public Role getRole() {
        return role;
    }
}
