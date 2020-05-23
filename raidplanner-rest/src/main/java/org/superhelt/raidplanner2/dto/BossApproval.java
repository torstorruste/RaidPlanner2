package org.superhelt.raidplanner2.dto;

import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.Role;

import java.util.List;

public class BossApproval {

    private final Character character;

    public BossApproval(Character character, List<Role> roles) {
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }
}
