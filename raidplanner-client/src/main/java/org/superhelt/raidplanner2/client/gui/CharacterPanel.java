package org.superhelt.raidplanner2.client.gui;

import org.superhelt.raidplanner2.client.service.PlayerService;
import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.CharacterClass;
import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.om.Role;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.net.URL;
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
        JTextField namePane = new JTextField(20);
        namePane.setText(character.getName());
        add(namePane);
        namePane.addActionListener(getNameListener());

        JComboBox<CharacterClass> classComboBox = new JComboBox<>(CharacterClass.values());
        classComboBox.setSelectedIndex(character.getCharacterClass().ordinal());
        classComboBox.addItemListener(getClassListener());
        add(classComboBox);

        for(Role role : Role.values()) {
            add(new JLabel(createImageIcon(role)));
            JCheckBox roleBox = new JCheckBox();
            if(character.getRoles().contains(role)) {
                roleBox.setSelected(true);
            }
            roleBox.addItemListener(getRoleListener(role));
            add(roleBox);
        }
    }

    private ActionListener getNameListener() {
        return a->{
            String newName = ((JTextField)a.getSource()).getText();
            if(!character.getName().equals(newName)) {
                character = new Character(character.getId(), newName, character.getCharacterClass(), character.getRoles());
                playerService.updateCharacter(player, character);
            }
        };
    }

    private ImageIcon createImageIcon(Role role) {
        URL url = getClass().getResource("/" + role+".png");
        if(url != null) {
            return new ImageIcon(url, role.toString());
        } else {
            return null;
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
