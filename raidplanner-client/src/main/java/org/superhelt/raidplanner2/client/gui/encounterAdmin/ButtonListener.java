package org.superhelt.raidplanner2.client.gui.encounterAdmin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ButtonListener implements MouseListener {

    private static final Logger log = LoggerFactory.getLogger(ButtonListener.class);

    private final JTable table;

    public ButtonListener(JTable table) {
        this.table = table;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = table.rowAtPoint(e.getPoint());
        int column = table.columnAtPoint(e.getPoint());
        log.debug("Mouse clicked row={}, column={}", row, column);

        if(column>1) {
            Object value = table.getValueAt(row, column);
            if(value instanceof JButton) {
                ((JButton)value).doClick();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
