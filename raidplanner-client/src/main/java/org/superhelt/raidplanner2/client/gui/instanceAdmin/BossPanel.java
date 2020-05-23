package org.superhelt.raidplanner2.client.gui.instanceAdmin;

import org.superhelt.raidplanner2.om.Boss;

import javax.swing.*;

public class BossPanel extends JPanel {

    private Boss boss;

    public BossPanel(Boss boss) {
        this.boss = boss;

        initGui();
    }

    private void initGui() {
        add(new JLabel(boss.getName()));
    }
}
