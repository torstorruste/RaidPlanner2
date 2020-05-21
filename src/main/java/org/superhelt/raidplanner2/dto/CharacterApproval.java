package org.superhelt.raidplanner2.dto;

import org.superhelt.raidplanner2.om.Boss;
import org.superhelt.raidplanner2.om.Role;

import java.util.List;

public class CharacterApproval {

    private final Boss boss;
    private final List<Role> roles;

    public CharacterApproval(Boss boss, List<Role> roles) {
        this.boss = boss;
        this.roles = roles;
    }

    public Boss getBoss() {
        return boss;
    }

    public List<Role> getRoles() {
        return roles;
    }
}
