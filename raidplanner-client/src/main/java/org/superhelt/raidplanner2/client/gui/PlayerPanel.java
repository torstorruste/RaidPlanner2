package org.superhelt.raidplanner2.client.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(PlayerPanel.class);

    private final PlayerService playerService;
    private final PlayerAdminPanel parent;
    private Player player;

    private List<CharacterPanel> characterPanels = new ArrayList<>();

    public PlayerPanel(PlayerService playerService, PlayerAdminPanel parent, Player player) {
        this.playerService = playerService;
        this.parent = parent;
        this.player = player;

        initGui();
    }

    public void setPlayer(Player player) {
        this.player = player;
        refresh();
    }

    private void initGui() {
        JButton addCharacter = new JButton(getAddCharacterAction());
        add(addCharacter);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        addCharacterPanels(player);
    }

    private void addCharacterPanels(Player player) {
        player.getCharacters().sort(Comparator.comparing(Character::getName));
        for (Character character : player.getCharacters()) {
            CharacterPanel panel = new CharacterPanel(this, playerService, player, character);
            characterPanels.add(panel);
            add(panel);
        }
    }

    private void refreshCharacterPanels(Player player) {
        for (CharacterPanel panel : characterPanels) {
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
        log.info("Refreshing player panel for {}", player.getName());
        refreshCharacterPanels(player);
        revalidate();
        repaint();
    }
}
