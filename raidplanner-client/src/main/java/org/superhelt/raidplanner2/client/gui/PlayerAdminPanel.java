package org.superhelt.raidplanner2.client.gui;

import org.superhelt.raidplanner2.client.service.PlayerService;
import org.superhelt.raidplanner2.om.Player;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PlayerAdminPanel extends JPanel {

    private final PlayerService service;
    private final JFrame program;

    private List<JPanel> panels = new ArrayList<>();

    public PlayerAdminPanel(JFrame program, PlayerService service) {
        this.program = program;
        this.service = service;

        initGui();
    }

    private void initGui() {
        for(JPanel panel : panels) {
            remove(panel);
        }
        panels.clear();

        List<Player> players = service.getPlayers();
        players.sort(Comparator.comparing(Player::getName));

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        for(Player player : players) {
            PlayerPanel playerPanel = new PlayerPanel(service, this, player);
            add(playerPanel);
            panels.add(playerPanel);
        }

        AddPlayerPanel addPanel = new AddPlayerPanel(this, service);
        add(addPanel);
        panels.add(addPanel);
    }

    public void refresh() {
        initGui();
        repaint();
        program.pack();
    }
}
