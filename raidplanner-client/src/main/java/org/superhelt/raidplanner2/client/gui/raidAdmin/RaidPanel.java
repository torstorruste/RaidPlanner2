package org.superhelt.raidplanner2.client.gui.raidAdmin;

import org.superhelt.raidplanner2.om.Raid;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class RaidPanel extends JPanel {

    private static final DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Raid raid;

    public RaidPanel(Raid raid) {
        this.raid = raid;

        intiGui();
    }

    private void intiGui() {
        add(new JLabel(dt.format(raid.getDate())));
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
