package org.superhelt.raidplanner2.client.gui.instanceAdmin;

import org.superhelt.raidplanner2.client.service.InstanceService;
import org.superhelt.raidplanner2.om.Boss;
import org.superhelt.raidplanner2.om.Instance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Comparator;

public class InstancePanel extends JPanel {

    private final InstanceService service;
    private final InstanceAdminPanel parent;

    private Instance instance;

    public InstancePanel(InstanceService service, InstanceAdminPanel parent, Instance instance) {
        this.service = service;
        this.parent = parent;
        this.instance = instance;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        initGui();
    }

    private void initGui() {
        if(instance!=null) {
            add(new JButton(getAddBossAction()));
            add(new JButton(getDeleteInstanceAction()));

            instance.getBosses().sort(Comparator.comparing(Boss::getId));
            for (Boss boss : instance.getBosses()) {
                add(new BossPanel(service, this, instance, boss));
            }
        }
    }

    private Action getDeleteInstanceAction() {
        return new AbstractAction("Delete instance") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(InstancePanel.this, "Are you sure you want to delete " + instance.getName() + "?",
                        "Delete " + instance.getName(), JOptionPane.YES_NO_OPTION);

                if(result==0) {
                    service.deleteInstance(instance);
                    parent.refreshPanel(instance);
                }
            }
        };
    }

    private Action getAddBossAction() {
        return new AbstractAction("Add Boss") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Boss boss = new Boss(-1, "Boss");
                Boss savedBoss = service.addBoss(instance, boss);

                instance.getBosses().add(savedBoss);
                setInstance(instance);
            }
        };
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
