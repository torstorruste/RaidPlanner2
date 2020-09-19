package org.superhelt.raidplanner2.client.gui.encounterAdmin;

import org.superhelt.raidplanner2.om.Encounter;
import org.superhelt.raidplanner2.om.EncounterCharacter;
import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.om.Raid;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class SelectPlayersPanel extends JPanel {
    private final Raid raid;
    private final Encounter encounter;
    private final List<Player> players;
    private final EncounterCharacterPanel encounterCharacterPanel;

    public SelectPlayersPanel(Raid raid, Encounter encounter, List<Player> players, EncounterCharacterPanel encounterCharacterPanel) {
        this.raid = raid;
        this.encounter = encounter;
        this.players = players;
        this.encounterCharacterPanel = encounterCharacterPanel;

        initGui();
    }

    private void initGui() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(new JLabel("Available players"));

        // For each player that isn't already in the encounter:
        List<Player> missingPlayers = getMissingPlayers(players, raid, encounter);
        for(Player player : missingPlayers) {
            add(new SelectCharacterPanel(player, encounter, encounterCharacterPanel));
        }
    }

    private List<Player> getMissingPlayers(List<Player> players, Raid raid, Encounter encounter) {
        List<Integer> selectedPlayers = encounter.getCharacters().stream().map(EncounterCharacter::getPlayerId).collect(Collectors.toList());

        return players.stream()
                .filter(p->!selectedPlayers.contains(p.getId())
                            && raid.getSignedUp().contains(p.getId()))
                .collect(Collectors.toList());
    }
}
