package org.superhelt.raidplanner2.client.gui.instanceAdmin;

import org.superhelt.raidplanner2.client.service.InstanceService;
import org.superhelt.raidplanner2.om.Boss;
import org.superhelt.raidplanner2.om.Instance;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;

public class InstancePanel extends JPanel {

    private final InstanceService service;

    private Instance instance;

    public InstancePanel(InstanceService service, Instance instance) {
        this.service = service;
        this.instance = instance;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        initGui();
    }

    private void initGui() {
        instance.getBosses().sort(Comparator.comparing(Boss::getId));
        for(Boss boss : instance.getBosses()) {
            add(new BossPanel(service, instance, boss));
        }
    }

    public void setInstance(Instance instance) {
        for(Component component : getComponents()) {
            remove(component);
        }
        this.instance = instance;
        initGui();
        revalidate();
        repaint();
    }
}
