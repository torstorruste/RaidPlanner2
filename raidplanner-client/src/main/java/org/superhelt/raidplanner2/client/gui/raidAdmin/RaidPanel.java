package org.superhelt.raidplanner2.client.gui.raidAdmin;

import org.superhelt.raidplanner2.client.gui.cellRenderers.PlayerCellRenderer;
import org.superhelt.raidplanner2.client.service.RaidService;
import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.om.Raid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RaidPanel extends JPanel {

    private static final DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final RaidService service;
    private List<Player> players;
    private Raid raid;
    private DefaultListModel<Player> leftListModel;
    private DefaultListModel<Player> rightListModel;
    private JList<Player> leftList;
    private JList<Player> rightList;

    public RaidPanel(RaidService service, Raid raid, List<Player> players) {
        this.service = service;
        this.players = players;
        this.raid = raid;

        intiGui();
    }

    private void intiGui() {
        add(new JLabel(dt.format(raid.getDate())));

        leftListModel = new DefaultListModel<>();
        players.forEach(leftListModel::addElement);

        rightListModel = new DefaultListModel<>();

        leftList = new JList<>(leftListModel);
        leftList.setCellRenderer(new PlayerCellRenderer());

        rightList = new JList<>(rightListModel);
        rightList.setCellRenderer(new PlayerCellRenderer());

        add(new JScrollPane(leftList));
        add(new JScrollPane(rightList));
        add(new JButton(moveRightAction()));
        add(new JButton(moveLeftAction()));
    }

    private Action moveLeftAction() {
        return new AbstractAction("<-") {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Player player : rightList.getSelectedValuesList()) {
                    rightListModel.removeElement(player);
                    leftListModel.addElement(player);
                    service.unsign(raid, player);
                }
            }
        };
    }

    private Action moveRightAction() {
        return new AbstractAction("->") {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Player player : leftList.getSelectedValuesList()) {
                    leftListModel.removeElement(player);
                    rightListModel.addElement(player);
                    service.signup(raid, player);
                }
            }
        };
    }

    public void setRaid(Raid raid) {
        this.raid = raid;
        for(Component component : getComponents()) {
            remove(component);
        }

        intiGui();

        revalidate();
        repaint();
    }
}
