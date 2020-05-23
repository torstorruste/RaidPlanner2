package org.superhelt.raidplanner2.client.gui.approvalAdmin;

import org.superhelt.raidplanner2.client.service.ApprovalService;
import org.superhelt.raidplanner2.client.service.InstanceService;
import org.superhelt.raidplanner2.client.service.PlayerService;
import org.superhelt.raidplanner2.om.Approval;
import org.superhelt.raidplanner2.om.Instance;
import org.superhelt.raidplanner2.om.Player;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ApprovalAdminPanel extends JPanel {

    private final ApprovalService approvalService;
    private final InstanceService instanceService;
    private final PlayerService playerService;

    public ApprovalAdminPanel(ApprovalService approvalService, InstanceService instanceService, PlayerService playerService) {
        this.approvalService = approvalService;
        this.instanceService = instanceService;
        this.playerService = playerService;

        initGui();
    }

    private void initGui() {
        List<Instance> instances = instanceService.getInstances();
        List<Player> players = playerService.getPlayers();
        List<Approval> approvals = approvalService.getApprovals();

        // TODO: Figure out how we want to add approval for more characters at a time
        ApprovalTableModel model = new ApprovalTableModel(approvalService, instances, players.subList(0, 1), approvals);
        JTable table = new JTable(model);

        add(new JScrollPane(table));
    }

}
