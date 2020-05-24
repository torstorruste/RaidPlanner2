package org.superhelt.raidplanner2.client.gui.approvalAdmin;

import org.superhelt.raidplanner2.client.service.ApprovalService;
import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.*;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.List;

public class ApprovalTableModel implements TableModel {

    private final ApprovalService approvalService;
    private final Instance instance;
    private final Player player;

    private final List<Approval> approvals;

    public ApprovalTableModel(ApprovalService approvalService, Instance instance, Player player, List<Approval> approvals) {
        this.approvalService = approvalService;
        this.instance = instance;
        this.player = player;
        this.approvals = approvals;
    }

    @Override
    public int getRowCount() {
        return instance.getBosses().size();
    }

    @Override
    public int getColumnCount() {
        return player.getCharacters().size() + 1;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch(columnIndex) {
            case 0: return "Boss";
            default: return player.getCharacters().get(columnIndex-1).getName();
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
            case 0: return instance.getBosses().get(rowIndex).getName();
            default:
                Boss boss = instance.getBosses().get(rowIndex);
                Character character = player.getCharacters().get(columnIndex-1);

                return existsApprovalFor(boss, character);
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Boss boss = instance.getBosses().get(rowIndex);
        Character character = player.getCharacters().get(columnIndex - 1);

        if((Boolean)aValue) {
            approvals.add(new Approval(character, boss));
            approvalService.addApproval(instance, boss, player, character);
        } else {
            approvals.removeIf(a->a.getCharacter().getId()==character.getId() && a.getBoss().getId()==boss.getId());
            approvalService.removeApproval(instance, boss, player, character);
        }
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
