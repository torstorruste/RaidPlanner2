package org.superhelt.raidplanner2.client.gui.encounterAdmin;

import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.Role;

import javax.swing.*;

public class SelectCharacterPanel extends JPanel {

    private final Player player;

    public SelectCharacterPanel(Player player) {
        this.player = player;

        initGui();
    }

    private void initGui() {
        add(new JLabel(player.getName()));
        for(Character character : player.getCharacters()) {
            // TODO: Only show if character is approved for the boss
            add(new JLabel(String.format(character.getName(), character.getCharacterClass(), "%s - %s")));

            for(Role role : character.getRoles()) {
                // TODO: Make it into a button that adds the character to the encounter
                add(new JLabel(role.createImageIcon()));
            }
        }
    }
}
