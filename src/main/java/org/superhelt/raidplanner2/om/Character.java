package org.superhelt.raidplanner2.om;

import java.util.List;

public class Character {

    private String name;
    private CharacterClass characterClass;
    private List<Role> roles;

    public Character() {
    }

    public Character(String name, CharacterClass characterClass, List<Role> roles) {
        this.name = name;
        this.characterClass = characterClass;
        this.roles = roles;
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
