package org.superhelt.raidplanner2.client.gui;

import org.superhelt.raidplanner2.client.service.PlayerService;
import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.CharacterClass;
import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.om.Role;

import javax.swing.*;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class CharacterPanel extends JPanel {

    private final PlayerService playerService;
    private final Player player;
    private Character character;

    public CharacterPanel(PlayerService playerService, Player player, Character character) {
        this.playerService = playerService;
        this.player = player;
        this.character = character;

        initGui();
    }

    private void initGui() {
        JTextPane namePane = new JTextPane();
        namePane.setText(character.getName());
        add(namePane);

        JComboBox<CharacterClass> classComboBox = new JComboBox<>(CharacterClass.values());
        classComboBox.setSelectedIndex(character.getCharacterClass().ordinal());
        classComboBox.addItemListener(getClassListener());
        add(classComboBox);

        for(Role role : Role.values()) {
            JCheckBox roleBox = new JCheckBox();
            if(character.getRoles().contains(role)) {
                roleBox.setSelected(true);
            }
            roleBox.addItemListener(getRoleListener(role));
            add(roleBox);
        }
    }

    private ItemListener getClassListener() {
        return a->{
            CharacterClass selectedClass = (CharacterClass) a.getItem();

            if(selectedClass != character.getCharacterClass()) {
                character = new Character(character.getId(), character.getName(), selectedClass, character.getRoles());
                playerService.updateCharacter(player, character);
            }
        };
    }

    private ItemListener getRoleListener(Role role) {
        return a-> {
            List<Role> newRoles = new ArrayList<>(character.getRoles());
            JCheckBox roleBox = (JCheckBox) a.getItem();
            if (roleBox.isSelected()) {
                newRoles.add(role);
            } else {
                newRoles.remove(role);
            }
            character = new Character(character.getId(), character.getName(), character.getCharacterClass(), newRoles);
            playerService.updateCharacter(player, character);
        };
    }

}
