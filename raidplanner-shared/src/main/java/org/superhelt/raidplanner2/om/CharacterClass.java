package org.superhelt.raidplanner2.om;

import java.awt.*;

public enum CharacterClass {
    DeathKnight(new Color(196, 31, 59)),
    DemonHunter(new Color(163, 48, 201)),
    Druid(new Color(255, 125, 10)),
    Hunter(new Color(169,210,113)),
    Mage(new Color(64,199,235)),
    Monk(new Color(0,255,150)),
    Paladin(new Color(245,140,186)),
    Priest(new Color(255,255,255)),
    Rogue(new Color(255,245,105)),
    Shaman(new Color(0,112,222)),
    Warlock(new Color(135,135,237)),
    Warrior(new Color(199,156,110));

    private final Color color;

    public Color getColor() {
        return color;
    }

    private CharacterClass(Color color) {
        this.color = color;
    }
}
