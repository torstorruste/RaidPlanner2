package org.superhelt.raidplanner2.client.gui.instanceAdmin;

import org.superhelt.raidplanner2.om.Instance;

import javax.swing.*;
import java.awt.*;

public class InstanceCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        return super.getListCellRendererComponent(list, ((Instance)value).getName(), index, isSelected, cellHasFocus);
    }


}
