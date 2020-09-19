package org.superhelt.raidplanner2.client.gui.cellRenderers;

import org.superhelt.raidplanner2.om.Boss;
import org.superhelt.raidplanner2.om.Encounter;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Optional;

public class EncounterCellRenderer extends DefaultListCellRenderer {

    private final List<Boss> bosses;

    public EncounterCellRenderer(List<Boss> bosses) {
        this.bosses = bosses;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        return super.getListCellRendererComponent(list, getBossName(((Encounter)value).getBossId()), index, isSelected, cellHasFocus);
    }

    public String getBossName(int id) {
        Optional<Boss> boss = bosses.stream().filter(b -> b.getId() == id).findFirst();
        if(boss.isPresent()) return boss.get().getName();
        else return String.format("Unknown boss (id=%d)", id);
    }
}
