package org.superhelt.raidplanner2.client.gui.encounterAdmin;

import org.superhelt.raidplanner2.client.gui.IconUtil;
import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.Encounter;
import org.superhelt.raidplanner2.om.EncounterCharacter;
import org.superhelt.raidplanner2.om.Player;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PickedPlayersModel implements TableModel {

    private final Encounter encounter;
    private final List<EncounterCharacter> encounterCharacters;
    private final Map<Integer, Character> characterMap;
    private final PickedPlayersPanel pickedPlayersPanel;

    public PickedPlayersModel(Encounter encounter, List<Player> players, PickedPlayersPanel pickedPlayersPanel) {
        this.encounter = encounter;

        this.encounterCharacters = new ArrayList<>(encounter.getCharacters());
        encounterCharacters.sort(Comparator.comparing(EncounterCharacter::getRole)
                .thenComparing(EncounterCharacter::getCharacterId));

        characterMap = players.stream()
                .flatMap(p->p.getCharacters().stream())
                .collect(Collectors.toMap(Character::getId, Function.identity()));
        this.pickedPlayersPanel = pickedPlayersPanel;
    }

    @Override
    public int getRowCount() {
        return encounter.getCharacters().size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0: return "Role";
            case 1: return "Name";
            case 2: return "Remove";
            default: throw new IllegalArgumentException("Unknown column: "+columnIndex);
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: return ImageIcon.class;
            case 1: return Character.class;
            case 2: return JButton.class;
            default: throw new IllegalArgumentException("Unknown column: "+columnIndex);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        EncounterCharacter encounterCharacter = encounterCharacters.get(rowIndex);
        Character character = characterMap.get(encounterCharacter.getCharacterId());

        switch(columnIndex) {
            case 0: return IconUtil.getRoleIcon(encounterCharacter.getRole());
            case 1: return character;
            case 2: return new JButton(new AbstractAction(null, IconUtil.getIcon("delete")) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pickedPlayersPanel.removeCharacter(character);
                }
            });
            default: throw new IllegalArgumentException("Unknown column: "+columnIndex);
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }
}
