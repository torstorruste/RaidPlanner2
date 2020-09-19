package org.superhelt.raidplanner2.client.gui.instanceAdmin;

import org.superhelt.raidplanner2.client.gui.IconUtil;
import org.superhelt.raidplanner2.client.service.InstanceService;
import org.superhelt.raidplanner2.om.Boss;
import org.superhelt.raidplanner2.om.Instance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Comparator;

public class BossPanel extends JPanel {

    private final InstanceService service;
    private final InstancePanel parent;

    private static final Dimension SIZE = new Dimension(700, 32);

    private final Instance instance;
    private Boss boss;

    public BossPanel(InstanceService service, InstancePanel parent, Instance instance, Boss boss) {
        this.service = service;
        this.parent = parent;
        this.instance = instance;
        this.boss = boss;

        initGui();
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

    private void initGui() {
        JTextField nameField = new JTextField(20);
        nameField.setText(boss.getName());
        nameField.addFocusListener(getFocusListener());
        nameField.addActionListener(getActionListener());
        add(nameField);

        add(new JButton(getDeleteAction()));
    }

    private Action getDeleteAction() {
        return new AbstractAction(null, IconUtil.getDeleteIcon()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                instance.getBosses().remove(boss);
                service.deleteBoss(instance, boss);
                parent.setInstance(instance);
            }
        };
    }

    private ActionListener getActionListener() {
        return a->{
            String newName = ((JTextField)a.getSource()).getText();
            if(!boss.getName().equals(newName)) {
                updateInstance(new Boss(boss.getId(), newName));
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
                    updateInstance(new Boss(boss.getId(), newName));
                    service.updateBoss(instance, boss);
                }
            }
        };
    }

    private void updateInstance(Boss boss) {
        instance.getBosses().remove(this.boss);
        this.boss = boss;
        instance.getBosses().add(boss);
        instance.getBosses().sort(Comparator.comparing(Boss::getId));
    }
}
