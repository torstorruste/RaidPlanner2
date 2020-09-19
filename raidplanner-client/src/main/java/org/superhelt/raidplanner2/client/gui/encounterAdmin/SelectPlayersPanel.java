package org.superhelt.raidplanner2.client.gui.encounterAdmin;

import org.superhelt.raidplanner2.client.service.EncounterService;
import org.superhelt.raidplanner2.om.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class SelectPlayersPanel extends JPanel {

    private final EncounterService encounterService;
    private final List<Approval> approvals;
    private final Raid raid;
    private final Encounter encounter;
    private final List<Player> players;
    private final EncounterCharacterPanel encounterCharacterPanel;

    public SelectPlayersPanel(EncounterService encounterService, Raid raid, Encounter encounter, List<Player> players,
                              EncounterCharacterPanel encounterCharacterPanel, List<Approval> approvals) {
        this.encounterCharacterPanel = encounterCharacterPanel;
        this.raid = raid;
        this.encounter = encounter;
        this.players = players;
        this.encounterService = encounterService;
        this.approvals = approvals;

        initGui();
    }

    private void initGui() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weighty = 0;
        c.gridy = -1;
        c.gridx = 0;

        add(new JLabel("Available players"), c);

        // For each player that isn't already in the encounter:
        List<Player> missingPlayers = getMissingPlayers(players, raid, encounter);
        for(Player player : missingPlayers) {
            add(new SelectCharacterPanel(player, encounter, this, approvals), c);
        }

        c.weighty = 1;
        add(new JPanel(), c);
    }

    public void addEncounterCharacter(EncounterCharacter encounterCharacter) {
        encounterService.addCharacter(raid, encounter, encounterCharacter);
        encounter.getCharacters().add(encounterCharacter);
        encounterCharacterPanel.setEncounter(encounter);
    }

    private List<Player> getMissingPlayers(List<Player> players, Raid raid, Encounter encounter) {
        List<Integer> selectedPlayers = encounter.getCharacters().stream().map(EncounterCharacter::getPlayerId).collect(Collectors.toList());

        return players.stream()
                .filter(p->!selectedPlayers.contains(p.getId())
                            && raid.getSignedUp().contains(p.getId()))
                .collect(Collectors.toList());
    }
}
