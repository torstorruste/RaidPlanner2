package org.superhelt.raidplanner2.client.gui;

import org.superhelt.raidplanner2.client.service.PlayerService;
import org.superhelt.raidplanner2.om.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class PlayerAdminPanel extends JPanel {

    private final PlayerService service;
    private final JFrame program;

    public PlayerAdminPanel(JFrame program, PlayerService service) {
        this.program = program;
        this.service = service;

        initGui();
    }

    private void initGui() {
        List<Player> players = service.getPlayers();

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        for(Player player : players) {
            add(new PlayerPanel(service, this, player));
        }
    }

    public void refresh() {
        repaint();
        program.pack();
    }
}
