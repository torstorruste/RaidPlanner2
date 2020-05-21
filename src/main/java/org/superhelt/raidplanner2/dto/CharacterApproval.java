package org.superhelt.raidplanner2.dto;

import org.superhelt.raidplanner2.om.Boss;
import org.superhelt.raidplanner2.om.Role;

public class CharacterApproval {

    private final Boss boss;
    private final Role role;

    public CharacterApproval(Boss boss, Role role) {
        this.boss = boss;
        this.role = role;
    }

    public Boss getBoss() {
        return boss;
    }

    public Role getRole() {
        return role;
    }
}
