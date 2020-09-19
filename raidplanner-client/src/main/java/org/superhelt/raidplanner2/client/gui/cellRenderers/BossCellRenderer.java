package org.superhelt.raidplanner2.client.gui.cellRenderers;

import org.superhelt.raidplanner2.om.Boss;

import javax.swing.*;
import java.awt.*;

public class BossCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        return super.getListCellRendererComponent(list, ((Boss)value).getName(), index, isSelected, cellHasFocus);
    }
}
