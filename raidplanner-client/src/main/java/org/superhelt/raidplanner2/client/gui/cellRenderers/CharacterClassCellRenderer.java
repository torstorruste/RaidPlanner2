package org.superhelt.raidplanner2.client.gui.cellRenderers;

import org.superhelt.raidplanner2.client.gui.IconUtil;
import org.superhelt.raidplanner2.om.CharacterClass;

import javax.swing.*;
import java.awt.*;

public class CharacterClassCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        CharacterClass characterClass = (CharacterClass) value;
        return new JLabel(characterClass.toString(), IconUtil.getClassIcon(characterClass), 0);
    }
}
