package org.superhelt.raidplanner2.dto;

import org.superhelt.raidplanner2.om.Boss;
import org.superhelt.raidplanner2.om.Role;

import java.util.List;

public class CharacterApproval {

    private final Boss boss;

    public CharacterApproval(Boss boss) {
        this.boss = boss;
    }

    public Boss getBoss() {
        return boss;
    }
}
