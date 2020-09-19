package org.superhelt.raidplanner2.client.gui.approvalAdmin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.client.gui.cellRenderers.CharacterRenderer;
import org.superhelt.raidplanner2.client.gui.cellRenderers.InstanceCellRenderer;
import org.superhelt.raidplanner2.client.gui.cellRenderers.PlayerCellRenderer;
import org.superhelt.raidplanner2.client.service.ApprovalService;
import org.superhelt.raidplanner2.client.service.InstanceService;
import org.superhelt.raidplanner2.client.service.PlayerService;
import org.superhelt.raidplanner2.om.Approval;
import org.superhelt.raidplanner2.om.Boss;
import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.Instance;
import org.superhelt.raidplanner2.om.Player;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
    private JPanel comboBoxPanel;

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

        currentPlayer = playerBox.getItemAt(0);
        currentInstance = instanceBox.getItemAt(0);

        comboBoxPanel = new JPanel();
        comboBoxPanel.add(playerBox);
        comboBoxPanel.add(instanceBox);

        comboBoxPanel.add(createCheckBoxes(currentPlayer, currentInstance, approvals));

        add(comboBoxPanel, BorderLayout.NORTH);

        ApprovalTableModel model = new ApprovalTableModel(approvalService, currentInstance, currentPlayer, approvals);
        table = new JTable(model);
        table.getColumnModel().getColumn(1).setHeaderRenderer(new CharacterRenderer(currentPlayer));

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private JPanel createCheckBoxes(Player player, Instance instance, List<Approval> approvals) {
        JPanel panel = new JPanel();
        for(int i=0;i<player.getCharacters().size(); i++) {
            JCheckBox checkAllBox = new JCheckBox();
            checkAllBox.addMouseListener(getSelectAllAction(checkAllBox, i+1));

            boolean shouldBeChecked = true;
            for(Boss boss : instance.getBosses()) {
                if(!isApproved(approvals, player.getCharacters().get(i), boss)) {
                    shouldBeChecked = false;
                }
            }
            checkAllBox.setSelected(shouldBeChecked);

            panel.add(checkAllBox, BorderLayout.NORTH);
        }

        return panel;
    }

    private boolean isApproved(List<Approval> approvals, Character character, Boss boss) {
        return approvals.stream().filter(a->a.getCharacter().getId()==character.getId())
                .anyMatch(a->a.getBoss().getId()==boss.getId());
    }

    private MouseListener getSelectAllAction(JCheckBox checkBox, int characterIndex) {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for(int i=0;i<table.getRowCount();i++) {
                    table.getModel().setValueAt(checkBox.isSelected(), i, characterIndex);
                    revalidate();
                    repaint();
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
        };
    }

    private ItemListener getSelectedPlayerAction() {
        return e -> {
            Player player = (Player)e.getItem();

            if(player.getId() != currentPlayer.getId()) {
                log.info("Selecting player {}", player.getName());
                currentPlayer = player;

                refresh();
            }
        };
    }


    private ItemListener getSelectedInstanceAction() {
        return e -> {
            Instance instance = (Instance) e.getItem();

            if(instance.getId() != currentInstance.getId()) {
                log.info("Selecting instance {}", instance.getName());
                currentInstance = instance;

                refresh();
            }
        };
    }

    private void refresh() {
        ApprovalTableModel model = new ApprovalTableModel(approvalService, currentInstance, currentPlayer, approvals);
        table.setModel(model);
        for(int i=0;i<currentPlayer.getCharacters().size();i++) {
            table.getColumnModel().getColumn(i+1).setHeaderRenderer(new CharacterRenderer(currentPlayer));
        }
        table.repaint();

        comboBoxPanel.remove(2);
        comboBoxPanel.add(createCheckBoxes(currentPlayer, currentInstance, approvals));

        revalidate();
        repaint();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JTabbedPane tabPane = (JTabbedPane) e.getSource();

        if (tabPane.getSelectedIndex() == 2) {
            log.info("Approval admin tab selected, refreshing pane");

            for(Component component : getComponents()) {
                remove(component);
            }

            initGui();
        }
    }
}
