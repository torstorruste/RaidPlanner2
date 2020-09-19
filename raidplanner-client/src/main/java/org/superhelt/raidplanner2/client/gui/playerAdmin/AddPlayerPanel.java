package org.superhelt.raidplanner2.client.gui.playerAdmin;

import org.superhelt.raidplanner2.client.gui.IconUtil;
import org.superhelt.raidplanner2.client.service.PlayerService;
import org.superhelt.raidplanner2.om.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class AddPlayerPanel extends JPanel {

    private final PlayerAdminPanel parent;
    private final PlayerService playerService;

    private JTextField nameField;

    public AddPlayerPanel(PlayerAdminPanel parent, PlayerService playerService) {
        this.parent = parent;
        this.playerService = playerService;

        initGui();
    }

    private void initGui() {
        nameField = new JTextField(10);
        add(nameField);

        JButton addPlayerButton = new JButton(getAddAction());
        add(addPlayerButton);
    }

    private Action getAddAction() {
        return new AbstractAction(null, IconUtil.getIcon("add")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!nameField.getText().isEmpty()) {
                    Player savedPlayer = playerService.addPlayer(new Player(-1, nameField.getText(), new ArrayList<>()));
                    parent.refreshPanel(savedPlayer);
                    nameField.setText("");
                }
            }
        };
    }
}
