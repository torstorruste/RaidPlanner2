package org.superhelt.raidplanner2.om;

public class Approval {

    private Player player;
    private Character character;
    private Instance instance;
    private Boss boss;

    public Approval() {
    }

    public Approval(Player player, Character character, Instance instance, Boss boss) {
        this.player = player;
        this.character = character;
        this.instance = instance;
        this.boss = boss;
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
}
