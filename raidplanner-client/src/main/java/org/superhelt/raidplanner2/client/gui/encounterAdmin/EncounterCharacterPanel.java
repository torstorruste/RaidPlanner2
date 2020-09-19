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

        setLayout(new GridBagLayout());
        initGui();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(2000, 2000);
    }

    private void initGui() {
        if(encounter!=null) {
            GridBagConstraints c = new GridBagConstraints();

            c.gridx = 0;
            c.gridy = 0;
            c.anchor = GridBagConstraints.NORTHWEST;
            c.fill = GridBagConstraints.NONE;
            c.weightx = 0;
            c.weighty = 0;

            Optional<Boss> boss = bosses.stream().filter(b->b.getId()==encounter.getBossId()).findFirst();
            if(boss.isPresent()) {
                add(new JLabel(String.format("%d: %s", raid.getId(), boss.get().getName())), c);
            } else {
                add(new JLabel(String.format("%d: Unknown boss", raid.getId())), c);
            }

            c.gridx = 1;
            add(new JButton(getDeleteEncounterAction(encounter)));

            c.gridy = 1;
            c.gridx = 0;
            // List all players that are part of the encounter
            add(new PickedPlayersPanel(encounterService, raid, encounter, players, this), c);

            c.gridx = 1;
            // List all players that have signed up and are not yet part of the encounter
            add(new SelectPlayersPanel(encounterService, raid, encounter, players, this, approvals), c);

            c.weighty = 1;
            c.weightx = 1;
            add(new JLabel(), c);
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
