package org.superhelt.raidplanner2.client.gui.raidAdmin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.client.gui.cellRenderers.PlayerCellRenderer;
import org.superhelt.raidplanner2.client.service.RaidService;
import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.om.Raid;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;

public class AddSignupPanel extends JPanel {

    private static final Logger log = LoggerFactory.getLogger(AddSignupPanel.class);

    private final RaidPanel parent;
    private final RaidService service;
    private final Raid raid;
    private final List<Player> players;

    private JComboBox<Player> playerList;

    public AddSignupPanel(RaidPanel parent, RaidService service, Raid raid, List<Player> players) {
        this.parent = parent;
        this.service = service;
        this.raid = raid;
        this.players = players;

        initGui();
    }

    private void initGui() {
        playerList = new JComboBox<>(getPlayersNotSignedUp());
        playerList.setRenderer(new PlayerCellRenderer());
        add(playerList);
        add(new JButton(getSignupAction()));
        add(new JButton(getSignupAllAction()));
    }

    private Player[] getPlayersNotSignedUp() {
        return players.stream().filter(p->!raid.getSignedUp().contains(p)).collect(Collectors.toList()).toArray(new Player[]{});
    }

    private Action getSignupAction() {
        return new AbstractAction("Add") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player player = (Player) playerList.getSelectedItem();
                if(player != null) {
                    signup(player);
                    parent.setRaid(raid);
                }
            }
        };
    }

    private Action getSignupAllAction() {
        return new AbstractAction("Add all") {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Player player : getPlayersNotSignedUp()) {
                    signup(player);
                }
                parent.setRaid(raid);
            }
        };
    }

    private void signup(Player player) {
        log.info("Signing up player {} to raid {}", player.getName(), raid.getDate());
        playerList.removeItem(player);
        raid.getSignedUp().add(player);
        service.signup(raid, player);
    }
}
