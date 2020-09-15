package org.superhelt.raidplanner2.client.gui.encounterAdmin;

import org.superhelt.raidplanner2.client.gui.cellRenderers.RaidCellRenderer;
import org.superhelt.raidplanner2.om.Encounter;
import org.superhelt.raidplanner2.om.Raid;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;

public class EncounterRaidPanel extends JSplitPane {

    private final Raid raid;

    private JList<Encounter> bossList;

    public EncounterRaidPanel(Raid raid) {
        this.raid = raid;

        initGui();
    }

    private void initGui() {
        bossList = new JList<>(raid.getEncounters().toArray(new Encounter[]{}));
        bossList.setCellRenderer(new RaidCellRenderer());
        bossList.addListSelectionListener(getEncounterListListener());
    }

    private ListSelectionListener getEncounterListListener() {
        return null;
    }


}
