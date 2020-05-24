package org.superhelt.raidplanner2.client.gui.raidAdmin;

import org.superhelt.raidplanner2.client.service.RaidService;
import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.om.Raid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SignupPanel extends JPanel {

    private final RaidPanel parent;
    private final RaidService service;
    private final Raid raid;
    private final Player player;

    private static final Dimension SIZE = new Dimension(700, 32);

    public SignupPanel(RaidPanel parent, RaidService service, Raid raid, Player player) {
        this.parent = parent;
        this.service = service;
        this.raid = raid;
        this.player = player;

        initGui();
    }

    private void initGui() {
        add(new JLabel(player.getName()));
        add(new JButton(getUnsignAction()));
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

    private Action getUnsignAction() {
        return new AbstractAction("Delete") {
            @Override
            public void actionPerformed(ActionEvent e) {
                raid.getSignedUp().removeIf(p->p.getId()==player.getId());
                parent.setRaid(raid);
            }
        };
    }
}
