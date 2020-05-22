package org.superhelt.raidplanner2.om;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return id == character.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
