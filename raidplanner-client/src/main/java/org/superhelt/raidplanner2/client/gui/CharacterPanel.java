package org.superhelt.raidplanner2.client.gui;

import org.superhelt.raidplanner2.client.service.PlayerService;
import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.CharacterClass;
import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.om.Role;

import javax.swing.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CharacterPanel extends JPanel {

    private final PlayerPanel parent;
    private final PlayerService playerService;
    private final Player player;
    private Character character;

    public CharacterPanel(PlayerPanel parent, PlayerService playerService, Player player, Character character) {
        this.parent = parent;
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
        namePane.addFocusListener(getNameFocusListener());

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
        add(new JButton(getDeleteButton()));
    }

    private FocusListener getNameFocusListener() {
        return new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Do nothing
            }

            @Override
            public void focusLost(FocusEvent e) {
                String newName = ((JTextField)e.getSource()).getText();
                if(!character.getName().equals(newName)) {
                    updateCharacter(new Character(character.getId(), newName, character.getCharacterClass(), character.getRoles()));
                    playerService.updateCharacter(player, character);
                }
            }
        };
    }

    private ActionListener getNameListener() {
        return a->{
            String newName = ((JTextField)a.getSource()).getText();
            if(!character.getName().equals(newName)) {
                updateCharacter(new Character(character.getId(), newName, character.getCharacterClass(), character.getRoles()));
                playerService.updateCharacter(player, character);
            }
        };
    }

    private Action getDeleteButton() {
        return new AbstractAction("Delete") {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.getCharacters().remove(character);
                playerService.deleteCharacter(player, character);
                parent.remove(CharacterPanel.this);
                parent.refresh();
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
                updateCharacter(new Character(character.getId(), character.getName(), selectedClass, character.getRoles()));
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
            updateCharacter(new Character(character.getId(), character.getName(), character.getCharacterClass(), newRoles));
            playerService.updateCharacter(player, character);
        };
    }

    private void updateCharacter(Character character) {
        player.getCharacters().remove(this.character);
        this.character = character;
        player.getCharacters().add(character);
    }

}
