package org.superhelt.raidplanner2.client.gui.instanceAdmin;

import org.superhelt.raidplanner2.om.Boss;
import org.superhelt.raidplanner2.om.Instance;

import javax.swing.*;

public class InstancePanel extends JPanel {

    private Instance instance;

    public InstancePanel(Instance instance) {
        this.instance = instance;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        initGui();
    }

    private void initGui() {
        add(new JLabel(instance.getName()));

        for(Boss boss : instance.getBosses()) {
            add(new BossPanel(boss));
        }

        add(new AddBossPanel());
    }
}
