package org.superhelt.raidplanner2.client.gui.encounterAdmin;

import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.Encounter;
import org.superhelt.raidplanner2.om.EncounterCharacter;
import org.superhelt.raidplanner2.om.Player;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class PickedPlayersPanel extends JPanel {

    private final Encounter encounter;
    private final List<Player> players;

    public PickedPlayersPanel(Encounter encounter, List<Player> players) {
        this.encounter = encounter;
        this.players = players;

        initGui();
    }

    private void initGui() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        for(EncounterCharacter encounterCharacter : encounter.getCharacters()) {
            Player player = getPlayer(encounterCharacter.getPlayerId());
            Character character = player.getCharacter(encounterCharacter.getCharacterId());

            add(new JLabel(String.format("%s - %s", player.getName(), character.getName())));
        }
    }

    private Player getPlayer(int id) {
        return players.stream().filter(p->p.getId()==id).findFirst().orElse(new Player(id, "Unknown", Collections.emptyList()));
    }
}
