package org.superhelt.raidplanner2.client.gui.encounterAdmin;

import org.superhelt.raidplanner2.client.gui.IconUtil;
import org.superhelt.raidplanner2.om.*;
import org.superhelt.raidplanner2.om.Character;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SelectPlayerModel implements TableModel {

    private final List<Character> characters = new ArrayList<>();
    private final Map<Character, Player> characterMap = new HashMap<>();
    private final SelectPlayersPanel selectPlayersPanel;

    public SelectPlayerModel(List<Player> players, Raid raid, Encounter encounter, List<Approval> approvals, SelectPlayersPanel selectPlayersPanel) {
        this.selectPlayersPanel = selectPlayersPanel;

        for(Player player : getMissingPlayers(players, raid, encounter)) {
            for(Character character : player.getCharacters()) {
                if(isApproved(character, encounter, approvals)) {
                    characters.add(character);
                    characterMap.put(character, player);
                }
            }
        }
    }

    @Override
    public int getRowCount() {
        return characters.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0: return "Player";
            case 1: return "Character";
            case 2: return "Tank";
            case 3: return "Healer";
            case 4: return "Melee";
            case 5: return "Ranged";
            default: throw new IllegalArgumentException("Unknown column "+columnIndex);
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0: return String.class;
            case 1: return Character.class;
            default: return JButton.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Character character = characters.get(rowIndex);
        Player player = characterMap.get(character);

        switch(columnIndex) {
            case 0: return player.getName();
            case 1: return character;
            case 2: return getButton(character, Role.Tank);
            case 3: return getButton(character, Role.Healer);
            case 4: return getButton(character, Role.Melee);
            case 5: return getButton(character, Role.Ranged);
        }

        throw new IllegalArgumentException("Unknown column: "+columnIndex);
    }

    private JComponent getButton(Character character, Role role) {
        if(character.getRoles().contains(role)) {
            return new JButton(new AbstractAction(null, IconUtil.getRoleIcon(role)) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Player player = characterMap.get(character);
                    EncounterCharacter encounterCharacter = new EncounterCharacter(player.getId(), character.getId(), role);
                    selectPlayersPanel.addEncounterCharacter(encounterCharacter);
                }
            });
        } else {
            return new JLabel();
        }
    }

    private boolean isApproved(Character character, Encounter encounter, List<Approval> approvals) {
        return approvals.stream()
                .filter(a->a.getCharacter().getId()==character.getId())
                .anyMatch(a->a.getBoss().getId()==encounter.getBossId());
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

    private List<Player> getMissingPlayers(List<Player> players, Raid raid, Encounter encounter) {
        List<Integer> selectedPlayers = encounter.getCharacters().stream().map(EncounterCharacter::getPlayerId).collect(Collectors.toList());

        return players.stream()
                .filter(p->!selectedPlayers.contains(p.getId())
                        && raid.getSignedUp().contains(p.getId()))
                .collect(Collectors.toList());
    }
}
