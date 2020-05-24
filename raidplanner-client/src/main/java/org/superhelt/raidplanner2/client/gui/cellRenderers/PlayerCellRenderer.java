package org.superhelt.raidplanner2.client.gui.cellRenderers;

import org.superhelt.raidplanner2.om.Player;

import javax.swing.*;
import java.awt.*;

public class PlayerCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if(value==null) return new JLabel();
        return super.getListCellRendererComponent(list, ((Player)value).getName(), index, isSelected, cellHasFocus);
    }
}
