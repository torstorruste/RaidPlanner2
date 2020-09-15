package org.superhelt.raidplanner2.om;

public class EncounterCharacter {

    private int playerId;
    private int characterId;
    private Role role;

    public EncounterCharacter() {
    }

    public EncounterCharacter(int playerId, int characterId, Role role) {
        this.playerId = playerId;
        this.characterId = characterId;
        this.role = role;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getCharacterId() {
        return characterId;
    }

    public Role getRole() {
        return role;
    }
}
