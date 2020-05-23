package org.superhelt.raidplanner2.client.gui.instanceAdmin;

import org.superhelt.raidplanner2.client.service.InstanceService;
import org.superhelt.raidplanner2.om.Boss;
import org.superhelt.raidplanner2.om.Instance;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class BossPanel extends JPanel {

    private final InstanceService service;

    private final Instance instance;
    private Boss boss;

    public BossPanel(InstanceService service, Instance instance, Boss boss) {
        this.service = service;
        this.instance = instance;
        this.boss = boss;

        initGui();
    }

    private void initGui() {
        JTextField nameField = new JTextField(20);
        nameField.setText(boss.getName());
        nameField.addFocusListener(getFocusListener());
        nameField.addActionListener(getActionListener());
        add(nameField);
    }

    private ActionListener getActionListener() {
        return a->{
            String newName = ((JTextField)a.getSource()).getText();
            if(!boss.getName().equals(newName)) {
                boss = new Boss(boss.getId(), newName);
                service.updateBoss(instance, boss);
            }
        };
    }

    private FocusListener getFocusListener() {
        return new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Do nothing
            }

            @Override
            public void focusLost(FocusEvent e) {
                String newName = ((JTextField) e.getSource()).getText();
                if(!boss.getName().equals(newName)) {
                    boss = new Boss(boss.getId(), newName);
                    service.updateBoss(instance, boss);
                }
            }
        };
    }
}
