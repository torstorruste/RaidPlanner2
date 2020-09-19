package org.superhelt.raidplanner2.client.gui.encounterAdmin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.client.gui.IconUtil;
import org.superhelt.raidplanner2.client.gui.cellRenderers.BossCellRenderer;
import org.superhelt.raidplanner2.client.gui.cellRenderers.EncounterCellRenderer;
import org.superhelt.raidplanner2.client.service.EncounterService;
import org.superhelt.raidplanner2.om.*;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EncounterRaidPanel extends JSplitPane {

    private static final Logger log = LoggerFactory.getLogger(EncounterRaidPanel.class);

    private final EncounterService encounterService;
    private final List<Instance> instances;
    private final List<Player> players;
    private final List<Approval> approvals;

    private Raid raid;

    private JList<Encounter> encounterList;
    private JComboBox<Boss> bossComboBox;
    private EncounterCharacterPanel characterPanel;

    public EncounterRaidPanel(EncounterService encounterService, Raid raid, List<Instance> instances, List<Player> players, List<Approval> approvals) {
        this.encounterService = encounterService;
        this.raid = raid;
        this.instances = instances;
        this.players = players;
        this.approvals = approvals;

        initGui();
    }

    private void initGui() {
        if(raid!=null) {
            log.info("Initializing EncounterRaidPanel with raid {}", raid.getId());
            List<Boss> allBosses = instances.stream().flatMap(i -> i.getBosses().stream()).collect(Collectors.toList());
            raid.getEncounters().sort(Comparator.comparing(Encounter::getBossId));
            encounterList = new JList<>(raid.getEncounters().toArray(new Encounter[]{}));
            encounterList.setCellRenderer(new EncounterCellRenderer(allBosses));
            encounterList.addListSelectionListener(getEncounterListListener());

            List<Boss> availableBosses = instances.stream().flatMap(i -> i.getBosses().stream()).collect(Collectors.toList());
            List<Integer> existingBosses = raid.getEncounters().stream().map(Encounter::getBossId).collect(Collectors.toList());

            availableBosses.removeIf(b -> existingBosses.contains(b.getId()));

            bossComboBox = new JComboBox<>(availableBosses.toArray(new Boss[]{}));
            bossComboBox.setRenderer(new BossCellRenderer());
            bossComboBox.addItemListener(getAddEncounterAction());

            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.weighty = 0;
            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(new GridBagLayout());
            leftPanel.add(encounterList, c);
            leftPanel.add(bossComboBox, c);
            leftPanel.add(new JButton(getAddAction()), c);
            c.weighty = 1;
            leftPanel.add(new JPanel(), c);
            setLeftComponent(leftPanel);

            characterPanel = new EncounterCharacterPanel(encounterService, raid, allBosses, players, encounterList.getSelectedValue(), approvals, this);
            setRightComponent(new JScrollPane(characterPanel));
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
            int selectedIndex = encounterList.getSelectedIndex();
            if(!e.getValueIsAdjusting() && selectedIndex >=0) {
                Encounter encounter = raid.getEncounters().get(selectedIndex);
                log.debug("Setting encounter {}", encounter.getId());

                characterPanel.setEncounter(encounter);
            }
        };
    }


    public ItemListener getAddEncounterAction() {
        return e->{
            // TODO: Implement
        };
    }private Action getAddAction() {
        return new AbstractAction(null, IconUtil.getAddIcon()) {
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
