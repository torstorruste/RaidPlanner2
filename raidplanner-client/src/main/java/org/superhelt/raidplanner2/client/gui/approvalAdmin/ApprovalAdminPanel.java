package org.superhelt.raidplanner2.client.gui.approvalAdmin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.client.gui.cellRenderers.InstanceCellRenderer;
import org.superhelt.raidplanner2.client.gui.cellRenderers.PlayerCellRenderer;
import org.superhelt.raidplanner2.client.service.ApprovalService;
import org.superhelt.raidplanner2.client.service.InstanceService;
import org.superhelt.raidplanner2.client.service.PlayerService;
import org.superhelt.raidplanner2.om.Approval;
import org.superhelt.raidplanner2.om.Instance;
import org.superhelt.raidplanner2.om.Player;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ItemListener;
import java.util.List;

public class ApprovalAdminPanel extends JPanel implements ChangeListener {

    private static final Logger log = LoggerFactory.getLogger(ApprovalAdminPanel.class);

    private final ApprovalService approvalService;
    private final InstanceService instanceService;
    private final PlayerService playerService;

    private JComboBox<Player> playerBox;
    private JComboBox<Instance> instanceBox;
    private Player currentPlayer;
    private Instance currentInstance;
    private List<Approval> approvals;
    private JTable table;

    public ApprovalAdminPanel(ApprovalService approvalService, InstanceService instanceService, PlayerService playerService) {
        this.approvalService = approvalService;
        this.instanceService = instanceService;
        this.playerService = playerService;

        initGui();
    }

    private void initGui() {
        setLayout(new BorderLayout());

        List<Instance> instances = instanceService.getInstances();
        List<Player> players = playerService.getPlayers();
        approvals = approvalService.getApprovals();

        playerBox = new JComboBox<>(players.toArray(new Player[]{}));
        playerBox.setRenderer(new PlayerCellRenderer());
        playerBox.addItemListener(getSelectedPlayerAction());

        instanceBox = new JComboBox<>(instances.toArray(new Instance[]{}));
        instanceBox.setRenderer(new InstanceCellRenderer());
        instanceBox.addItemListener(getSelectedInstanceAction());

        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.add(playerBox);
        comboBoxPanel.add(instanceBox);

        add(comboBoxPanel, BorderLayout.NORTH);

        currentPlayer = playerBox.getItemAt(0);
        currentInstance = instanceBox.getItemAt(0);

        ApprovalTableModel model = new ApprovalTableModel(approvalService, currentInstance, currentPlayer, approvals);
        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private ItemListener getSelectedPlayerAction() {
        return e -> {
            Player player = (Player)e.getItem();

            if(player.getId() != currentPlayer.getId()) {
                log.info("Selecting player {}", player.getName());
                currentPlayer = player;

                ApprovalTableModel model = new ApprovalTableModel(approvalService, currentInstance, currentPlayer, approvals);
                table.setModel(model);
                table.repaint();

                revalidate();
                repaint();
            }
        };
    }


    private ItemListener getSelectedInstanceAction() {
        return e -> {
            Instance instance = (Instance) e.getItem();

            if(instance.getId() != currentInstance.getId()) {
                log.info("Selecting instance {}", instance.getName());
                currentInstance = instance;

                ApprovalTableModel model = new ApprovalTableModel(approvalService, currentInstance, currentPlayer, approvals);
                table.setModel(model);
                table.repaint();
                revalidate();
                repaint();
            }
        };
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JTabbedPane tabPane = (JTabbedPane) e.getSource();

        if(tabPane.getSelectedIndex()==2) {
            log.info("Approval admin selected, updating players and instances");

            List<Instance> instances = instanceService.getInstances();
            List<Player> players = playerService.getPlayers();

            playerBox.setModel(new DefaultComboBoxModel<>(players.toArray(new Player[]{})));
            instanceBox.setModel(new DefaultComboBoxModel<>(instances.toArray(new Instance[]{})));
        }
    }
}
