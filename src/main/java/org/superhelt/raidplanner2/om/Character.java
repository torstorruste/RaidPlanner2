package org.superhelt.raidplanner2.om;

import java.util.List;

public class Character {

    private int id;
    private String name;
    private CharacterClass characterClass;
    private List<Role> roles;

    public Character() {
    }

    public Character(int id, String name, CharacterClass characterClass, List<Role> roles) {
        this.id = id;
        this.name = name;
        this.characterClass = characterClass;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public List<Role> getRoles() {
        return roles;
    }
}
