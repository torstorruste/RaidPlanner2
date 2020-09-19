package org.superhelt.raidplanner2.client.gui.encounterAdmin;

import org.superhelt.raidplanner2.client.gui.IconUtil;
import org.superhelt.raidplanner2.client.service.EncounterService;
import org.superhelt.raidplanner2.om.Encounter;
import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.Raid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PickedCharacterPanel extends JPanel {

    private final EncounterCharacterPanel encounterCharacterPanel;
    private final EncounterService encounterService;
    private final Raid raid;
    private final Encounter encounter;
    private final Player player;
    private final Character character;

    public PickedCharacterPanel(EncounterCharacterPanel encounterCharacterPanel, EncounterService encounterService, Raid raid, Encounter encounter, Player player, Character character) {
        this.encounterCharacterPanel = encounterCharacterPanel;
        this.encounterService = encounterService;
        this.raid = raid;
        this.encounter = encounter;
        this.player = player;
        this.character = character;

        initGui();
    }

    private void initGui() {
        add(IconUtil.getClassLabel(character));
        add(new JButton(getDeleteAction(character)));
    }

    private Action getDeleteAction(Character character) {
        return new AbstractAction(null, IconUtil.getDeleteIcon()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                encounterService.deleteCharacter(raid, encounter, character);
                encounter.getCharacters().removeIf(c->c.getCharacterId()==character.getId());
                encounterCharacterPanel.setEncounter(encounter);
            }
        };
    }
}
