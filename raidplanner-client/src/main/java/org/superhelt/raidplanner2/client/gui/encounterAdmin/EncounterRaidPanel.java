package org.superhelt.raidplanner2.client.gui.encounterAdmin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.client.gui.cellRenderers.BossCellRenderer;
import org.superhelt.raidplanner2.client.gui.cellRenderers.EncounterCellRenderer;
import org.superhelt.raidplanner2.client.service.EncounterService;
import org.superhelt.raidplanner2.om.*;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EncounterRaidPanel extends JPanel {

    private static final Logger log = LoggerFactory.getLogger(EncounterRaidPanel.class);

    private final EncounterService encounterService;
    private final List<Instance> instances;


    private Raid raid;

    private JList<Encounter> encounterList;
    private JComboBox<Boss> bossComboBox;

    public EncounterRaidPanel(EncounterService encounterService, Raid raid, List<Instance> instances) {
        this.encounterService = encounterService;
        this.raid = raid;
        this.instances = instances;

        initGui();
    }

    private void initGui() {
        if(raid!=null) {
            log.info("Initializing EncounterRaidPanel with raid {}", raid.getId());
            List<Boss> allBosses = instances.stream().flatMap(i -> i.getBosses().stream()).collect(Collectors.toList());
            encounterList = new JList<>(raid.getEncounters().toArray(new Encounter[]{}));
            encounterList.setCellRenderer(new EncounterCellRenderer(allBosses));
            encounterList.addListSelectionListener(getEncounterListListener());

            add(encounterList);

            List<Boss> availableBosses = instances.stream().flatMap(i -> i.getBosses().stream()).collect(Collectors.toList());
            List<Integer> existingBosses = raid.getEncounters().stream().map(Encounter::getBossId).collect(Collectors.toList());

            availableBosses.removeIf(b -> existingBosses.contains(b.getId()));

            bossComboBox = new JComboBox<>(availableBosses.toArray(new Boss[]{}));
            bossComboBox.setRenderer(new BossCellRenderer());
            bossComboBox.addItemListener(getAddEncounterAction());
            add(bossComboBox);

            add(new JButton(getAddAction()));
        }
    }

    public void setRaid(Raid raid) {
        this.raid = raid;
        log.debug("Removing all components");
        for(Component component : getComponents()) {
            remove(component);
        }

        initGui();
        revalidate();
        repaint();
    }

    private ListSelectionListener getEncounterListListener() {
        return e->{
            // TODO: Implement
        };
    }


    public ItemListener getAddEncounterAction() {
        return e->{
            // TODO: Implement
        };
    }private Action getAddAction() {
        return new AbstractAction("Add") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Boss boss = (Boss) bossComboBox.getSelectedItem();
                Encounter encounter = encounterService.addEncounter(raid, boss);
                raid.getEncounters().add(encounter);

                setRaid(raid);
            }
        };
    }
}
