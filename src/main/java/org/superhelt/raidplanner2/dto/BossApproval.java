package org.superhelt.raidplanner2.dto;

import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.Role;

public class BossApproval {

    private final Character character;
    private final Role role;

    public BossApproval(Character character, Role role) {
        this.character = character;
        this.role = role;
    }

    public Character getCharacter() {
        return character;
    }

    public Role getRole() {
        return role;
    }
}
