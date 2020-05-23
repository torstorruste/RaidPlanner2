package org.superhelt.raidplanner2.client.gui.approvalAdmin;

import org.superhelt.raidplanner2.client.service.ApprovalService;
import org.superhelt.raidplanner2.om.*;
import org.superhelt.raidplanner2.om.Character;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

public class ApprovalTableModel implements TableModel {

    private final ApprovalService approvalService;
    private final List<Instance> instances;
    private final List<Boss> bosses;
    private final List<Player> players;
    private final List<Character> characters;

    private final List<Approval> approvals;

    public ApprovalTableModel(ApprovalService approvalService, List<Instance> instances, List<Player> players, List<Approval> approvals) {
        this.approvalService = approvalService;
        this.instances = instances;
        bosses = new ArrayList<>();
        this.players = players;
        characters = new ArrayList<>();
        this.approvals = approvals;

        instances.forEach(i -> bosses.addAll(i.getBosses()));
        players.forEach(p -> characters.addAll(p.getCharacters()));
    }

    @Override
    public int getRowCount() {
        return bosses.size();
    }

    @Override
    public int getColumnCount() {
        return characters.size() + 1;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch(columnIndex) {
            case 0: return "Boss";
            default: return characters.get(columnIndex-1).getName();
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: return String.class;
            default: return Boolean.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex >= 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0: return bosses.get(rowIndex).getName();
            default:
                Boss boss = bosses.get(rowIndex);
                Character character = characters.get(columnIndex-1);

                return existsApprovalFor(boss, character);
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Boss boss = bosses.get(rowIndex);
        Instance instance = findInstance(boss);
        Character character = characters.get(columnIndex - 1);
        Player player = findPlayer(character);

        if((Boolean)aValue) {
            approvals.add(new Approval(character, boss));
            approvalService.addApproval(instance, boss, player, character);
        } else {
            approvals.removeIf(a->a.getCharacter().getId()==character.getId() && a.getBoss().getId()==boss.getId());
            approvalService.removeApproval(instance, boss, player, character);
        }
    }

    private Player findPlayer(Character character) {
        for(Player player : players) {
            if(player.getCharacters().contains(character)) {
                return player;
            }
        }

        throw new IllegalStateException("Could not find player for character "+character.getName());
    }

    private Instance findInstance(Boss boss) {
        for(Instance instance : instances) {
            if(instance.getBosses().contains(boss)) {
                return instance;
            }
        }

        throw new IllegalStateException("Could not find instance for boss "+boss.getName());
    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }

    private Boolean existsApprovalFor(Boss boss, Character character) {
        return approvals.stream().anyMatch(a->a.getBoss().getId()==boss.getId() && a.getCharacter().getId()==character.getId());
    }
}
