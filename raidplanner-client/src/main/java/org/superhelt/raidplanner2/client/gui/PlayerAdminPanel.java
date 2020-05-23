package org.superhelt.raidplanner2.client.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.client.service.PlayerService;
import org.superhelt.raidplanner2.om.Player;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.util.Comparator;
import java.util.List;

public class PlayerAdminPanel extends JPanel {

    private static final Logger log = LoggerFactory.getLogger(PlayerAdminPanel.class);

    private final PlayerService service;
    private List<Player> players;
    private PlayerPanel playerPanel;
    private JList<Player> list;

    public PlayerAdminPanel(PlayerService service) {
        this.service = service;

        initGui();
    }

    private void initGui() {
        list = new JList<>();
        DefaultListModel<Player> model = new DefaultListModel<>();
        players = service.getPlayers();
        players.sort(Comparator.comparing(Player::getName));

        players.forEach(model::addElement);

        list.setModel(model);
        list.setSelectedIndex(0);
        list.setCellRenderer(new PlayerCellRenderer());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        leftPanel.add(list);
        leftPanel.add(new AddPlayerPanel(this, service));

        playerPanel = new PlayerPanel(service, this, players.get(0));
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, playerPanel);
        add(splitPane);

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        list.addListSelectionListener(getListListener());
    }

    private ListSelectionListener getListListener() {
        return e->{
            if(list.getSelectedIndex()>=0) {
                Player player = players.get(list.getSelectedIndex());
                log.info("Selecting player {} (index={})", player.getName(), list.getSelectedIndex());
                playerPanel.setPlayer(player);
            }
        };
    }

    public void addPlayer(Player player) {
        log.info("Refreshing list with player {}", player.getName());
        players = service.getPlayers();
        players.sort(Comparator.comparing(Player::getName));

        int index = getIndex(players, player);

        DefaultListModel<Player> model = new DefaultListModel<>();
        players.forEach(model::addElement);
        list.setModel(model);
        list.setSelectedIndex(index);

        revalidate();
        repaint();
    }

    private int getIndex(List<Player> players, Player player) {
        for(int i=0;i<players.size();i++) {
            if(players.get(i).getId()==player.getId()) {
                return i;
            }
        }
        return -1;
    }
}
