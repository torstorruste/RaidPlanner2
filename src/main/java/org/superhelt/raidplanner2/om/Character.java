package org.superhelt.raidplanner2.om;

import java.util.List;

public class Character {

    private final String name;
    private final CharacterClass characterClass;
    private final List<Role> roles;

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
