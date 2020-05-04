package org.superhelt.raidplanner2.om;

import java.util.List;

public class Player {

    private final int id;
    private final String name;
    private final List<Character> characters;

    public Player(int id, String name, List<Character> characters) {
        this.id = id;
        this.name = name;
        this.characters = characters;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Character> getCharacters() {
        return characters;
    }
}
