package org.superhelt.raidplanner2.client.gui.playerAdmin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.client.gui.cellRenderers.PlayerCellRenderer;
import org.superhelt.raidplanner2.client.service.PlayerService;
import org.superhelt.raidplanner2.om.Player;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class PlayerAdminPanel extends JSplitPane implements ChangeListener {

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
        DefaultListModel<Player> model = new DefaultListModel<>();
        players = service.getPlayers();
        players.sort(Comparator.comparing(Player::getName));
        players.forEach(model::addElement);

        list = new JList<>();
        list.setModel(model);
        list.setSelectedIndex(0);
        list.setCellRenderer(new PlayerCellRenderer());
        list.setDragEnabled(false);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        leftPanel.add(list);
        leftPanel.add(new AddPlayerPanel(this, service));

        Player player = players.isEmpty()?null:players.get(0);
        playerPanel = new PlayerPanel(service, this, player);
        setLeftComponent(leftPanel);
        setRightComponent(playerPanel);
        setEnabled(false);

        list.addListSelectionListener(getListListener());
    }

    private ListSelectionListener getListListener() {
        return e->{
            int selectedIndex = list.getSelectedIndex();
            if(!e.getValueIsAdjusting() && selectedIndex >=0) {
                Player player = players.get(selectedIndex);
                log.info("Selecting player {} (index={})", player.getName(), selectedIndex);
                playerPanel.setPlayer(player);
            }
        };
    }

    public void refreshPanel(Player player) {
        log.info("Refreshing list with player {}", player.getName());
        players = service.getPlayers();
        players.sort(Comparator.comparing(Player::getName));

        int index = getIndex(players, player);

        DefaultListModel<Player> model = new DefaultListModel<>();
        players.forEach(model::addElement);
        list.setModel(model);
        list.setSelectedIndex(index);
    }

    private int getIndex(List<Player> players, Player player) {
        for(int i=0;i<players.size();i++) {
            if(players.get(i).getId()==player.getId()) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JTabbedPane tabPane = (JTabbedPane) e.getSource();

        if (tabPane.getSelectedIndex() == 0) {
            log.info("Player admin tab selected, refreshing pane");

            for(Component component : getComponents()) {
                remove(component);
            }

            initGui();
        }
    }
}
