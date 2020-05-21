package org.superhelt.raidplanner2.om;

public class Approval {

    private Player player;
    private Character character;
    private Instance instance;
    private Boss boss;
    private Role role;

    public Approval() {
    }

    public Approval(Player player, Character character, Instance instance, Boss boss, Role role) {
        this.player = player;
        this.character = character;
        this.instance = instance;
        this.boss = boss;
        this.role = role;
    }

    public Player getPlayer() {
        return player;
    }

    public Character getCharacter() {
        return character;
    }

    public Instance getInstance() {
        return instance;
    }

    public Boss getBoss() {
        return boss;
    }

    public Role getRole() {
        return role;
    }
}
