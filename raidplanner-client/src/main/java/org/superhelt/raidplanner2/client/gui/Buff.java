package org.superhelt.raidplanner2.client.gui;

import org.superhelt.raidplanner2.om.CharacterClass;

public enum Buff {
    ArcaneIntellect(CharacterClass.Mage),
    BattleShout(CharacterClass.Warrior),
    ChasoBrand(CharacterClass.DemonHunter),
    Fortitude(CharacterClass.Priest),
    MysticTouch(CharacterClass.Monk);

    public final CharacterClass characterClass;

    private Buff(CharacterClass characterClass) {
        this.characterClass = characterClass;
    }
}
