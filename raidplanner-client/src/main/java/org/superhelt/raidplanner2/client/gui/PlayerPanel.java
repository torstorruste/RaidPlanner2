package org.superhelt.raidplanner2.client.gui;

import org.superhelt.raidplanner2.client.service.PlayerService;
import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.CharacterClass;
import org.superhelt.raidplanner2.om.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PlayerPanel extends JPanel {

    private final PlayerService playerService;
    private final PlayerAdminPanel parent;
    private final Player player;

    private List<CharacterPanel> characterPanels = new ArrayList<>();

    public PlayerPanel(PlayerService playerService, PlayerAdminPanel parent, Player player) {
        this.playerService = playerService;
        this.parent = parent;
        this.player = player;

        initGui();
    }

    private void initGui() {
        add(new JLabel("Player: "+player.getName()));

        JButton addCharacter = new JButton(getAddCharacterAction());
        add(addCharacter);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        addCharacterPanels(player);
    }

    private void addCharacterPanels(Player player) {
        player.getCharacters().sort(Comparator.comparing(Character::getName));
        for(Character character : player.getCharacters()) {
            CharacterPanel panel = new CharacterPanel(this, playerService, player, character);
            characterPanels.add(panel);
            add(panel);
        }
    }

    private void refreshCharacterPanels(Player player) {
        for(CharacterPanel panel : characterPanels) {
            remove(panel);
        }
        characterPanels.clear();

        addCharacterPanels(player);
    }

    private Action getAddCharacterAction() {
        return new AbstractAction("Add Character") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Character character = new Character(-1, "Alt", CharacterClass.DeathKnight, new ArrayList<>());
                Character savedCharacter = playerService.addCharacter(player, character);

                player.getCharacters().add(savedCharacter);
                refresh();
            }
        };
    }

    public void refresh() {
        refreshCharacterPanels(player);
        repaint();
        parent.refresh();
    }
}
