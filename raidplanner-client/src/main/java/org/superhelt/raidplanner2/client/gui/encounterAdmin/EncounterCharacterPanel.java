package org.superhelt.raidplanner2.client.gui.encounterAdmin;

import org.superhelt.raidplanner2.client.gui.IconUtil;
import org.superhelt.raidplanner2.client.service.EncounterService;
import org.superhelt.raidplanner2.om.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Optional;

public class EncounterCharacterPanel extends JPanel {

    private final EncounterService encounterService;
    private final List<Approval> approvals;
    private final EncounterRaidPanel encounterRaidPanel;
    private final List<Boss> bosses;
    private final List<Player> players;
    private final Raid raid;

    private Encounter encounter;

    public EncounterCharacterPanel(EncounterService encounterService, Raid raid, List<Boss> bosses, List<Player> players,
                                   Encounter encounter, List<Approval> approvals, EncounterRaidPanel encounterRaidPanel) {
        this.raid = raid;
        this.encounter = encounter;
        this.bosses = bosses;
        this.players = players;
        this.encounterService = encounterService;
        this.approvals = approvals;
        this.encounterRaidPanel = encounterRaidPanel;

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
            add(new JButton(getDeleteEncounterAction(encounter)));

            // List all players that are part of the encounter
            add(new PickedPlayersPanel(encounterService, raid, encounter, players, this));

            // List all players that have signed up and are not yet part of the encounter
            add(new SelectPlayersPanel(encounterService, raid, encounter, players, this, approvals));
        } else {
            add(new JLabel(String.format("%d: Select encounter", raid.getId())));
        }
    }

    private Action getDeleteEncounterAction(Encounter encounter) {
        return new AbstractAction(null, IconUtil.getDeleteIcon()) {
            @Override
            public void actionPerformed(ActionEvent ev) {
                encounterService.deleteEncounter(raid, encounter);
                raid.getEncounters().removeIf(e->e.getId()==encounter.getId());

                encounterRaidPanel.setRaid(raid);
            }
        };
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
