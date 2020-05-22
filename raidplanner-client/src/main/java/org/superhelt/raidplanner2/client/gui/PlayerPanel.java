package org.superhelt.raidplanner2.client.gui;

import org.superhelt.raidplanner2.client.service.PlayerService;
import org.superhelt.raidplanner2.om.Player;

import javax.swing.*;
import java.util.List;

public class PlayerPanel extends JPanel {

    private final PlayerService service;

    public PlayerPanel(PlayerService service) {
        this.service = service;

        initGui();
    }

    private void initGui() {
        List<Player> players = service.getPlayers();

        for(Player player : players) {
            add(new JLabel(player.getName()));
        }
    }


}
