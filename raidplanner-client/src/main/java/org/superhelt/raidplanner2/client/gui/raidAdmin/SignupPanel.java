package org.superhelt.raidplanner2.client.gui.raidAdmin;

import org.superhelt.raidplanner2.client.gui.cellRenderers.PlayerCellRenderer;
import org.superhelt.raidplanner2.client.service.RaidService;
import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.om.Raid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SignupPanel extends JPanel {

    private final RaidService service;
    private final List<Player> players;
    private final Raid raid;

    private DefaultListModel<Player> leftListModel;
    private DefaultListModel<Player> rightListModel;
    private JList<Player> leftList;
    private JList<Player> rightList;

    public SignupPanel(RaidService service, Raid raid, List<Player> players) {
        this.service = service;
        this.players = players;
        this.raid = raid;

        intiGui();
    }

    private void intiGui() {
        leftListModel = new DefaultListModel<>();
        getUnsignedPlayers().forEach(leftListModel::addElement);

        rightListModel = new DefaultListModel<>();
        getSignedPlayers().forEach(rightListModel::addElement);

        leftList = new JList<>(leftListModel);
        leftList.setCellRenderer(new PlayerCellRenderer());

        rightList = new JList<>(rightListModel);
        rightList.setCellRenderer(new PlayerCellRenderer());


        add(createScrollPane(leftList, "Unsigned"));
        add(new JButton(moveRightAction()));
        add(new JButton(moveLeftAction()));
        add(createScrollPane(rightList, "Signed"));
    }

    private JComponent createScrollPane(JList<Player> list, String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JScrollPane scrollPane = new JScrollPane(list);
        Dimension dimension = new Dimension(100, 200);
        scrollPane.setSize(dimension);
        scrollPane.setPreferredSize(dimension);
        scrollPane.setMinimumSize(dimension);
        scrollPane.setMaximumSize(dimension);

        panel.add(new JLabel(title));
        panel.add(scrollPane);

        return panel;
    }

    private List<Player> getUnsignedPlayers() {
        return players.stream()
                .filter(p-> raid.getSignedUp().stream().noneMatch(s->s==p.getId()))
                .sorted(Comparator.comparing(Player::getName))
                .collect(Collectors.toList());
    }

    private List<Player> getSignedPlayers() {
        return raid.getSignedUp().stream().map(this::getPlayerById).sorted(Comparator.comparing(Player::getName)).collect(Collectors.toList());
    }

    private Player getPlayerById(int id) {
        return players.stream().
                filter(p->p.getId()==id)
                .findFirst().get();
    }

    private Action moveLeftAction() {
        return new AbstractAction("<-") {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Player player : rightList.getSelectedValuesList()) {
                    raid.getSignedUp().removeIf(p->p==player.getId());
                    service.unsign(raid, player);
                }

                refreshLists();
            }
        };
    }

    private Action moveRightAction() {
        return new AbstractAction("->") {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Player player : leftList.getSelectedValuesList()) {
                    raid.getSignedUp().add(player.getId());
                    service.signup(raid, player);
                }

                refreshLists();
            }
        };
    }

    private void refreshLists() {
        leftListModel.removeAllElements();
        getUnsignedPlayers().forEach(leftListModel::addElement);

        rightListModel.removeAllElements();
        getSignedPlayers().forEach(rightListModel::addElement);

        revalidate();
        repaint();
    }
}
