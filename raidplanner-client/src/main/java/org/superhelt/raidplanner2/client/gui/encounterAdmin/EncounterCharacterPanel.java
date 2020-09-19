package org.superhelt.raidplanner2.client.gui.encounterAdmin;

import org.superhelt.raidplanner2.om.Encounter;
import org.superhelt.raidplanner2.om.Raid;

import javax.swing.*;
import java.awt.*;

public class EncounterCharacterPanel extends JPanel {

    private Raid raid;
    private Encounter encounter;

    public EncounterCharacterPanel(Raid raid, Encounter encounter) {
        this.raid = raid;
        this.encounter = encounter;

        initGui();
    }

    private void initGui() {
        if(encounter!=null) {
            add(new JLabel(String.format("%d: %s", raid.getId(), encounter.getBossId())));
        } else {
            add(new JLabel(String.format("%d: Select encounter", raid.getId())));
        }
    }

    public void setEncounter(Encounter encounter) {
        this.encounter = encounter;
        for(Component component : getComponents()) {
            remove(component);
        }

        initGui();
        revalidate();
        repaint();
    }
}
