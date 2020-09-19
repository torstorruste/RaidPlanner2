package org.superhelt.raidplanner2.client.gui.encounterAdmin;

import org.superhelt.raidplanner2.client.gui.cellRenderers.TableCharacterRenderer;
import org.superhelt.raidplanner2.client.gui.cellRenderers.TableComponentRenderer;
import org.superhelt.raidplanner2.client.service.EncounterService;
import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.Encounter;
import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.om.Raid;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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

    private void initGui() {
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.weighty = 0;
        c.gridx = 0;
        c.gridy = -1;
        add(new JLabel("Selected characters"), c);

        JTable table = new JTable(new PickedPlayersModel(encounter, players, this));
        table.setDefaultRenderer(JButton.class, new TableComponentRenderer());
        table.setDefaultRenderer(Character.class, new TableCharacterRenderer());
        table.addMouseListener(new ButtonListener(table));
        add(table, c);
    }

    public void removeCharacter(Character character) {
        encounterService.deleteCharacter(raid, encounter, character);
        encounter.getCharacters().removeIf(c->c.getCharacterId()==character.getId());
        encounterCharacterPanel.setEncounter(encounter);
    }
}
