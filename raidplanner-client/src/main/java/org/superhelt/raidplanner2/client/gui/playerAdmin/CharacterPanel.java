package org.superhelt.raidplanner2.client.gui.playerAdmin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.client.gui.IconUtil;
import org.superhelt.raidplanner2.client.gui.cellRenderers.CharacterClassCellRenderer;
import org.superhelt.raidplanner2.client.service.PlayerService;
import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.CharacterClass;
import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.om.Role;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class CharacterPanel extends JPanel {

    private static final Logger log = LoggerFactory.getLogger(CharacterPanel.class);

    private final PlayerPanel parent;
    private final PlayerService playerService;
    private final Player player;
    private Character character;

    private static final Dimension SIZE = new Dimension(700, 32);

    public CharacterPanel(PlayerPanel parent, PlayerService playerService, Player player, Character character) {
        this.parent = parent;
        this.playerService = playerService;
        this.player = player;
        this.character = character;

        initGui();
    }

    @Override
    public Dimension getPreferredSize() {
        return SIZE;
    }

    @Override
    public Dimension getMaximumSize() {
        return SIZE;
    }

    @Override
    public Dimension getMinimumSize() {
        return SIZE;
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
        classComboBox.setRenderer(new CharacterClassCellRenderer());
        add(classComboBox);

        for(Role role : Role.values()) {
            add(new JLabel(IconUtil.getRoleIcon(role)));
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
                    refresh();
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
                refresh();
            }
        };
    }

    private Action getDeleteButton() {
        return new AbstractAction(null, IconUtil.getIcon("delete")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.getCharacters().remove(character);
                playerService.deleteCharacter(player, character);
                parent.refresh();
            }
        };
    }

    private ItemListener getClassListener() {
        return a->{
            CharacterClass selectedClass = (CharacterClass) a.getItem();

            if(selectedClass != character.getCharacterClass()) {
                updateCharacter(new Character(character.getId(), character.getName(), selectedClass, character.getRoles()));
                playerService.updateCharacter(player, character);
            }
            refresh();
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
            refresh();
        };
    }

    private void updateCharacter(Character character) {
        player.getCharacters().remove(this.character);
        this.character = character;
        player.getCharacters().add(character);
        refresh();
    }

    public void refresh() {
        log.info("Refreshing character panel for character {}", character.getName());
        revalidate();
        repaint();
    }

}
