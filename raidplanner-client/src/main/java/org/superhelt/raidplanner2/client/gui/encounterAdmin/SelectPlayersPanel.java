package org.superhelt.raidplanner2.client.gui.encounterAdmin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.client.gui.cellRenderers.TableCharacterRenderer;
import org.superhelt.raidplanner2.client.gui.cellRenderers.TableComponentRenderer;
import org.superhelt.raidplanner2.client.service.EncounterService;
import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.*;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EventObject;
import java.util.List;

public class SelectPlayersPanel extends JPanel {

    private static final Logger log = LoggerFactory.getLogger(SelectPlayersPanel.class);

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

        JTable table = new JTable(new SelectPlayerModel(players, raid, encounter, approvals, this));
        table.setDefaultRenderer(JButton.class, new TableComponentRenderer());
        table.setDefaultRenderer(Character.class, new TableCharacterRenderer());
        table.addMouseListener(new ButtonListener(table));
        add(table, c);
    }

    public void addEncounterCharacter(EncounterCharacter encounterCharacter) {
        encounterService.addCharacter(raid, encounter, encounterCharacter);
        encounter.getCharacters().add(encounterCharacter);
        encounterCharacterPanel.setEncounter(encounter);
    }
}
