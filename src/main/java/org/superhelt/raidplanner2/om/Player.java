package org.superhelt.raidplanner2.om;

import java.util.List;

public class Player {

    private final String name;
    private final List<Character> characters;

    public Player(String name, List<Character> characters) {
        this.name = name;
        this.characters = characters;
    }

    public String getName() {
        return name;
    }

    public List<Character> getCharacters() {
        return characters;
    }
}
