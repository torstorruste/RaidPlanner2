package org.superhelt.raidplanner2.dto;

import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.Role;

import java.util.List;

public class BossApproval {

    private final Character character;
    private final List<Role> roles;

    public BossApproval(Character character, List<Role> roles) {
        this.character = character;
        this.roles = roles;
    }

    public Character getCharacter() {
        return character;
    }

    public List<Role> getRoles() {
        return roles;
    }
}
