package org.superhelt.raidplanner2.client.gui;

import org.superhelt.raidplanner2.client.service.PlayerService;
import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.Player;

import javax.swing.*;

public class PlayerPanel extends JPanel {

    private final PlayerService playerService;
    private final Player player;

    public PlayerPanel(PlayerService playerService, Player player) {
        this.playerService = playerService;
        this.player = player;

        initGui();
    }

    private void initGui() {
        add(new JLabel("Player: "+player.getName()));

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        for(Character character : player.getCharacters()) {
            add(new CharacterPanel(playerService, player, character));
        }
    }


}
