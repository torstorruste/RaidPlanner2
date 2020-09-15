package org.superhelt.raidplanner2.client.gui.encounterAdmin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.client.gui.cellRenderers.RaidCellRenderer;
import org.superhelt.raidplanner2.client.service.PlayerService;
import org.superhelt.raidplanner2.client.service.RaidService;
import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.om.Raid;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.util.List;

public class EncounterAdminPanel extends JSplitPane {

    private static final Logger log = LoggerFactory.getLogger(EncounterAdminPanel.class);

    private final RaidService raidService;
    private final PlayerService playerService;

    private JList<Raid> raidList;
    private List<Raid> raids;

    public EncounterAdminPanel(RaidService raidService, PlayerService playerService) {
        this.raidService = raidService;
        this.playerService = playerService;

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
    }

    private ListSelectionListener getRaidListListener() {
        return a-> {
            log.debug("Raid selected");
        };
    }


}
