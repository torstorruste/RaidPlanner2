package org.superhelt.raidplanner2.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.client.gui.approvalAdmin.ApprovalAdminPanel;
import org.superhelt.raidplanner2.client.gui.encounterAdmin.EncounterAdminPanel;
import org.superhelt.raidplanner2.client.gui.instanceAdmin.InstanceAdminPanel;
import org.superhelt.raidplanner2.client.gui.playerAdmin.PlayerAdminPanel;
import org.superhelt.raidplanner2.client.gui.raidAdmin.RaidAdminPanel;
import org.superhelt.raidplanner2.client.service.ApprovalService;
import org.superhelt.raidplanner2.client.service.InstanceService;
import org.superhelt.raidplanner2.client.service.PlayerService;
import org.superhelt.raidplanner2.client.service.RaidService;

import javax.swing.*;
import java.awt.*;

public class Program extends JFrame {

    private static final Logger log = LoggerFactory.getLogger(Program.class);

    public Program() throws HeadlessException {
        super("A Necessary Raid Planner");

        PlayerService playerService = new PlayerService();
        InstanceService instanceService = new InstanceService();
        ApprovalService approvalService = new ApprovalService();
        RaidService raidService = new RaidService();

        JTabbedPane tabPane = new JTabbedPane();
        PlayerAdminPanel playerAdminPanel = new PlayerAdminPanel(playerService);
        ApprovalAdminPanel approvalAdminPanel = new ApprovalAdminPanel(approvalService, instanceService, playerService);
        InstanceAdminPanel instanceAdminPanel = new InstanceAdminPanel(instanceService);
        RaidAdminPanel raidAdminPanel = new RaidAdminPanel(raidService, playerService);
        EncounterAdminPanel encounterAdminPanel = new EncounterAdminPanel(raidService, playerService);

        tabPane.addTab("Players", playerAdminPanel);
        tabPane.addTab("Instances", instanceAdminPanel);
        tabPane.addTab("Approval", approvalAdminPanel);
        tabPane.addTab("Raids", raidAdminPanel);
        tabPane.addTab("Encounters", encounterAdminPanel);

        tabPane.addChangeListener(playerAdminPanel);
        tabPane.addChangeListener(instanceAdminPanel);
        tabPane.addChangeListener(approvalAdminPanel);
        tabPane.addChangeListener(raidAdminPanel);

        add(tabPane);
    }

    public static void main(String[] args) {
        Program program = new Program();
        program.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        program.pack();
        program.setVisible(true);
    }
}
