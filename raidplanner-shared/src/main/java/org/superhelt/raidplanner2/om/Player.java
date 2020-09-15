package org.superhelt.raidplanner2.om;

import java.util.Collections;
import java.util.List;

public class Player {

    private int id;
    private String name;
    private List<Character> characters;

    public Player() {
        characters = Collections.emptyList();
    }

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
