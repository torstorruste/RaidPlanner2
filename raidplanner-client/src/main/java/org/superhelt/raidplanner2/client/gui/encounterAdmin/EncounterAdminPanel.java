package org.superhelt.raidplanner2.client.gui.encounterAdmin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.client.gui.cellRenderers.RaidCellRenderer;
import org.superhelt.raidplanner2.client.service.EncounterService;
import org.superhelt.raidplanner2.client.service.InstanceService;
import org.superhelt.raidplanner2.client.service.PlayerService;
import org.superhelt.raidplanner2.client.service.RaidService;
import org.superhelt.raidplanner2.om.Instance;
import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.om.Raid;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.util.List;

public class EncounterAdminPanel extends JSplitPane {

    private static final Logger log = LoggerFactory.getLogger(EncounterAdminPanel.class);

    private final RaidService raidService;
    private final PlayerService playerService;
    private final InstanceService instanceService;
    private final EncounterService encounterService;

    private JList<Raid> raidList;
    private List<Raid> raids;
    private EncounterRaidPanel raidPanel;

    public EncounterAdminPanel(RaidService raidService, PlayerService playerService, InstanceService instanceService, EncounterService encounterService) {
        this.raidService = raidService;
        this.playerService = playerService;
        this.instanceService = instanceService;
        this.encounterService = encounterService;

        initGui();
    }

    private void initGui() {
        raids = raidService.getRaids();
        List<Player> players = playerService.getPlayers();

        raidList = new JList<>(raids.toArray(new Raid[]{}));
        raidList.setCellRenderer(new RaidCellRenderer());
        raidList.addListSelectionListener(getRaidListListener());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        leftPanel.add(raidList);

        setLeftComponent(leftPanel);
        setRightComponent(new JPanel());

        List<Instance> instances = instanceService.getInstances();
        raidPanel = new EncounterRaidPanel(encounterService, raidList.getSelectedValue(), instances);
        setRightComponent(raidPanel);
    }

    private ListSelectionListener getRaidListListener() {
        return e-> {
            int index = e.getFirstIndex();
            Raid raid = raids.get(index);
            log.debug("Raid selected: {}", raid.getId());
            raidPanel.setRaid(raid);

            revalidate();
            repaint();
        };
    }
}
