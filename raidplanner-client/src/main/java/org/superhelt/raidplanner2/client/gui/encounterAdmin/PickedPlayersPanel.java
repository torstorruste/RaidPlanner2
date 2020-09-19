package org.superhelt.raidplanner2.client.gui.encounterAdmin;

import org.superhelt.raidplanner2.client.gui.IconUtil;
import org.superhelt.raidplanner2.client.service.EncounterService;
import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PickedPlayersPanel extends JPanel {

    private final EncounterService encounterService;
    private final Raid raid;
    private final Encounter encounter;
    private final List<Player> players;
    private final EncounterCharacterPanel encounterCharacterPanel;

    public PickedPlayersPanel(EncounterService encounterService, Raid raid, Encounter encounter, List<Player> players, EncounterCharacterPanel encounterCharacterPanel) {
        this.encounterService = encounterService;
        this.raid = raid;
        this.encounter = encounter;
        this.players = players;
        this.encounterCharacterPanel = encounterCharacterPanel;

        initGui();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 1000);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(300, 1000);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(300, 1000);
    }

    private void initGui() {
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.weighty = 0;
        c.gridx = 0;
        c.gridy = -1;

        add(new JLabel("Selected characters"), c);

        for(Role role : Role.values()) {
            List<EncounterCharacter> charactersOfRole = getCharactersByRole(role);
            if(!charactersOfRole.isEmpty()) {
                JLabel roleHeader = new JLabel(String.format("%s (%d)", role.toString(), charactersOfRole.size()));
                add(roleHeader, c);

                for (EncounterCharacter encounterCharacter : charactersOfRole) {
                    Player player = getPlayer(encounterCharacter.getPlayerId());
                    Character character = player.getCharacter(encounterCharacter.getCharacterId());

                    JPanel characterPanel = new JPanel();
                    characterPanel.add(new JLabel(String.format("%s - %s", player.getName(), character.getName())));
                    characterPanel.add(new JButton(getDeleteAction(character)));
                    add(characterPanel, c);
                }
            }
        }
        c.weighty = 1;
        add(new JPanel(), c);
    }

    private Action getDeleteAction(Character character) {
        return new AbstractAction(null, IconUtil.getDeleteIcon()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                encounterService.deleteCharacter(raid, encounter, character);
                encounter.getCharacters().removeIf(c->c.getCharacterId()==character.getId());
                encounterCharacterPanel.setEncounter(encounter);
            }
        };
    }

    private List<EncounterCharacter> getCharactersByRole(Role role) {
        return encounter.getCharacters().stream().filter(c->c.getRole()==role).collect(Collectors.toList());
    }

    private Player getPlayer(int id) {
        return players.stream().filter(p->p.getId()==id).findFirst().orElse(new Player(id, "Unknown", Collections.emptyList()));
    }
}
