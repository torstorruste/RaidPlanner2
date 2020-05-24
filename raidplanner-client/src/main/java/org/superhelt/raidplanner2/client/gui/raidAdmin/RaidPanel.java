package org.superhelt.raidplanner2.client.gui.raidAdmin;

import org.superhelt.raidplanner2.client.service.RaidService;
import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.om.Raid;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RaidPanel extends JPanel {

    private static final DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final RaidService service;
    private final List<Player> players;
    private Raid raid;

    public RaidPanel(RaidService service, Raid raid, List<Player> players) {
        this.service = service;
        this.players = players;
        this.raid = raid;

        intiGui();
    }

    private void intiGui() {
        add(new JLabel(dt.format(raid.getDate())));

        add(new SignupPanel(service, raid, players));
    }

    public void setRaid(Raid raid) {
        this.raid = raid;
        for(Component component : getComponents()) {
            remove(component);
        }

        intiGui();

        revalidate();
        repaint();
    }
}
