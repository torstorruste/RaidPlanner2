package org.superhelt.raidplanner2.client.gui;

import org.superhelt.raidplanner2.om.CharacterClass;

public enum Buff {
    ArcaneIntellect(CharacterClass.Mage),
    BattleShout(CharacterClass.Warrior),
    ChasoBrand(CharacterClass.DemonHunter),
    Fortitude(CharacterClass.Priest),
    MysticTouch(CharacterClass.Monk),
    Rebirth(CharacterClass.Druid,CharacterClass.Warlock,CharacterClass.DeathKnight),
    Bloodlust(CharacterClass.Mage,CharacterClass.Shaman, CharacterClass.Hunter);

    public final CharacterClass[] characterClass;

    private Buff(CharacterClass... characterClass) {
        this.characterClass = characterClass;
    }
}
