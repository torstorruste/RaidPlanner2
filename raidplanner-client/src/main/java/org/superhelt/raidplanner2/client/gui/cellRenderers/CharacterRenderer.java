package org.superhelt.raidplanner2.client.gui.cellRenderers;

import org.superhelt.raidplanner2.client.gui.IconUtil;
import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.Player;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Optional;

public class CharacterRenderer extends DefaultTableCellRenderer {

    private final Player currentPlayer;

    public CharacterRenderer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Optional<Character> character = currentPlayer.getCharacters().stream().filter(c -> c.getName() == value).findFirst();
        if(character.isPresent()) {
            return IconUtil.getClassLabel(character.get());
        } else {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
}
