package org.superhelt.raidplanner2.client.gui.encounterAdmin;

import org.superhelt.raidplanner2.om.*;
import org.superhelt.raidplanner2.om.Character;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SelectCharacterPanel extends JPanel {

    private final Player player;
    private final Encounter encounter;
    private final SelectPlayersPanel selectPlayersPanel;

    public SelectCharacterPanel(Player player, Encounter encounter, SelectPlayersPanel selectPlayersPanel) {
        this.player = player;
        this.encounter = encounter;
        this.selectPlayersPanel = selectPlayersPanel;

        initGui();
    }

    private void initGui() {
        add(new JLabel(player.getName()));
        for(Character character : player.getCharacters()) {
            // TODO: Only show if character is approved for the boss
            add(new JLabel(String.format(character.getName(), character.getCharacterClass(), "%s - %s")));

            for(Role role : character.getRoles()) {
                JButton addCharacterButton = new JButton(role.createImageIcon());
                addCharacterButton.setAction(getAddCharacterAction(character, role));
                add(addCharacterButton);
            }
        }
    }

    private Action getAddCharacterAction(Character character, Role role) {
        return new AbstractAction(null, role.createImageIcon()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                EncounterCharacter encounterCharacter = new EncounterCharacter(player.getId(), character.getId(), role);
                selectPlayersPanel.addEncounterCharacter(encounterCharacter);
            }
        };
    }
}