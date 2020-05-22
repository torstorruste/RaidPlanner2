package org.superhelt.raidplanner2.client.gui;

import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.CharacterClass;
import org.superhelt.raidplanner2.om.Role;

import javax.swing.*;

public class CharacterPanel extends JPanel {

    private final Character character;

    public CharacterPanel(Character character) {
        this.character = character;

        initGui();
    }

    private void initGui() {
        JTextPane namePane = new JTextPane();
        namePane.setText(character.getName());
        add(namePane);

        JComboBox<CharacterClass> classComboBox = new JComboBox<>(CharacterClass.values());
        classComboBox.setSelectedIndex(character.getCharacterClass().ordinal());
        add(classComboBox);

        for(Role role : Role.values()) {
            JCheckBox roleBox = new JCheckBox();
            if(character.getRoles().contains(role)) {
                roleBox.setSelected(true);
            }
            add(roleBox);
        }
    }


}
