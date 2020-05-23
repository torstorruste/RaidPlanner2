package org.superhelt.raidplanner2.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.client.gui.instanceAdmin.InstanceAdminPanel;
import org.superhelt.raidplanner2.client.gui.playerAdmin.PlayerAdminPanel;
import org.superhelt.raidplanner2.client.service.InstanceService;
import org.superhelt.raidplanner2.client.service.PlayerService;

import javax.swing.*;
import java.awt.*;

public class Program extends JFrame {

    private static final Logger log = LoggerFactory.getLogger(Program.class);

    public Program() throws HeadlessException {
        super("A Necessary Raid Planner");

        PlayerService playerService = new PlayerService();
        InstanceService instanceService = new InstanceService();

        JTabbedPane tabPane = new JTabbedPane();
        tabPane.addTab("Players", new PlayerAdminPanel(playerService));
        tabPane.addTab("Instances", new InstanceAdminPanel(instanceService));

        add(tabPane);
    }

    public static void main(String[] args) {
        Program program = new Program();
        program.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        program.pack();
        program.setVisible(true);
    }
}
