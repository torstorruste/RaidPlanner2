package org.superhelt.raidplanner2.client.gui.encounterAdmin;

import org.superhelt.raidplanner2.client.gui.Buff;
import org.superhelt.raidplanner2.client.gui.IconUtil;
import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.*;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EncounterStatsPanel extends JPanel {
    private final Encounter encounter;
    private final List<Player> players;

    public EncounterStatsPanel(Encounter encounter, List<Player> players) {
        this.encounter = encounter;
        this.players = players;

        initGui();
    }

    private void initGui() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.weighty = 0;

        add(new JLabel("Roles:"), c);
        for(Role role : Role.values()) {
            add(new JLabel(String.valueOf(getNumberOfRole(encounter, role)), IconUtil.getRoleIcon(role), SwingConstants.LEFT), c);
        }

        add(new JLabel(), c);

        add(new JLabel("Classes:"), c);
        for(CharacterClass characterClass : CharacterClass.values()) {
            add(new JLabel(String.valueOf(getNumberOfClass(encounter, characterClass)), IconUtil.getClassIcon(characterClass), SwingConstants.LEFT), c);
        }

        add(new JLabel("Missing buffs"), c);
        for(Buff buff : Buff.values()) {
            boolean hasBuff = getNumberOfClass(encounter, buff.characterClass) > 0;
            if(!hasBuff) {
                add(new JLabel(buff.toString(), IconUtil.getBuffIcon(buff), SwingConstants.LEFT), c);
            }
        }
    }

    private Optional<Character> getCharacterById(int id) {
        return players.stream()
                .flatMap(p->p.getCharacters().stream())
                .filter(c->c.getId()==id)
                .findFirst();
    }

    private int getNumberOfClass(Encounter encounter, CharacterClass... characterClasses) {
        List<CharacterClass> classes = Arrays.asList(characterClasses);
        return (int) encounter.getCharacters().stream()
                .map(EncounterCharacter::getCharacterId)
                .map(this::getCharacterById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(c->classes.contains(c.getCharacterClass()))
                .count();
    }

    private int getNumberOfRole(Encounter encounter, Role role) {
        return (int) encounter.getCharacters().stream()
                .filter(c->c.getRole()==role).count();
    }
}
