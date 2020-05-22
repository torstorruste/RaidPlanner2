package org.superhelt.raidplanner2.client;

import org.superhelt.raidplanner2.client.gui.PlayerPanel;
import org.superhelt.raidplanner2.client.service.PlayerService;

import javax.swing.*;
import java.awt.*;

public class Program extends JFrame {

    public Program() throws HeadlessException {
        super("A Necessary Raid Planner");

        PlayerService playerService = new PlayerService();
        add(new PlayerPanel(playerService));
    }

    public static void main(String[] args) {
        Program program = new Program();
        program.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        program.pack();
        program.setVisible(true);
    }
}
