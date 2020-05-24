package org.superhelt.raidplanner2.client.gui.cellRenderers;

import org.superhelt.raidplanner2.om.Raid;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class RaidCellRenderer extends DefaultListCellRenderer {

    private static final DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        return super.getListCellRendererComponent(list, dt.format(((Raid)value).getDate()), index, isSelected, cellHasFocus);
    }
}
