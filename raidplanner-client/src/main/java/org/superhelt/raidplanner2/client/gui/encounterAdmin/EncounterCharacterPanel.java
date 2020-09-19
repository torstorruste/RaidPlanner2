package org.superhelt.raidplanner2.client.gui.encounterAdmin;

import org.superhelt.raidplanner2.om.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Optional;

public class EncounterCharacterPanel extends JPanel {

    private final List<Boss> bosses;
    private final List<Player> players;
    private final Raid raid;

    private Encounter encounter;

    public EncounterCharacterPanel(Raid raid, List<Boss> bosses, List<Player> players, Encounter encounter) {
        this.raid = raid;
        this.encounter = encounter;
        this.bosses = bosses;
        this.players = players;

        initGui();
    }

    private void initGui() {
        if(encounter!=null) {
            Optional<Boss> boss = bosses.stream().filter(b->b.getId()==encounter.getBossId()).findFirst();
            if(boss.isPresent()) {
                add(new JLabel(String.format("%d: %s", raid.getId(), boss.get().getName())));
            } else {
                add(new JLabel(String.format("%d: Unknown boss", raid.getId())));
            }

            // List all players that are part of the encounter
            add(new PickedPlayersPanel(encounter, players));

            // List all players that have signed up and are not yet part of the encounter
            add(new SelectPlayersPanel(raid, encounter, players));
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
