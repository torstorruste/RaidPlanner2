package org.superhelt.raidplanner2.client.gui.encounterAdmin;

import org.superhelt.raidplanner2.client.gui.IconUtil;
import org.superhelt.raidplanner2.om.*;
import org.superhelt.raidplanner2.om.Character;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class SelectCharacterPanel extends JPanel {

    private final Player player;
    private final Encounter encounter;
    private final SelectPlayersPanel selectPlayersPanel;
    private final List<Approval> approvals;

    public SelectCharacterPanel(Player player, Encounter encounter, SelectPlayersPanel selectPlayersPanel, List<Approval> approvals) {
        this.player = player;
        this.encounter = encounter;
        this.selectPlayersPanel = selectPlayersPanel;
        this.approvals = approvals;

        initGui();
    }

    @Override
    public Dimension getPreferredSize() {
        int height = 30;
        for(Character character : player.getCharacters()) {
            if(isApproved(character, encounter)) {
                height += 30;
            }
        }
        return new Dimension(300, height);
    }

    private void initGui() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(new JLabel(player.getName()));
        for(Character character : player.getCharacters()) {
            if(isApproved(character, encounter)){
                JPanel panel = new JPanel();
                panel.add(IconUtil.getClassLabel(character));

                for (Role role : character.getRoles()) {
                    JButton addCharacterButton = new JButton(role.createImageIcon());
                    addCharacterButton.setAction(getAddCharacterAction(character, role));
                    panel.add(addCharacterButton);
                }

                add(panel);
            }
        }
    }

    private boolean isApproved(Character character, Encounter encounter) {
        return approvals.stream()
                .filter(a->a.getCharacter().getId()==character.getId())
                .anyMatch(a->a.getBoss().getId()==encounter.getBossId());

    }

    private Action getAddCharacterAction(Character character, Role role) {
        return new AbstractAction(null, role.createImageIcon()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                EncounterCharacter encounterCharacter = new EncounterCharacter(player.getId(), character.getId(), role);
                selectPlayersPanel.addEncounterCharacter(encounterCharacter);
            }
        };
    }
}
