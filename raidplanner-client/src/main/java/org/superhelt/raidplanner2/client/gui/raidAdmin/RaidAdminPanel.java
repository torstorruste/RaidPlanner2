package org.superhelt.raidplanner2.client.gui.raidAdmin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.client.gui.cellRenderers.RaidCellRenderer;
import org.superhelt.raidplanner2.client.service.RaidService;
import org.superhelt.raidplanner2.om.Raid;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionListener;
import java.util.List;

public class RaidAdminPanel extends JSplitPane implements ChangeListener {

    private static final Logger log = LoggerFactory.getLogger(RaidAdminPanel.class);

    private final RaidService service;
    private JList<Raid> list;
    private RaidPanel raidPanel;
    private List<Raid> raids;

    public RaidAdminPanel(RaidService service) {
        this.service = service;

        initGui();
    }

    private void initGui() {
        raids = service.getRaids();

        setEnabled(false);

        list = new JList<>(raids.toArray(new Raid[]{}));
        list.setCellRenderer(new RaidCellRenderer());
        list.addListSelectionListener(getListListener());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        leftPanel.add(list);
        leftPanel.add(new AddRaidPanel(this, service));

        setLeftComponent(leftPanel);

        raidPanel = new RaidPanel(raids.get(0));
        setRightComponent(raidPanel);
    }

    private ListSelectionListener getListListener() {
        return a-> {
            Raid raid = list.getSelectedValue();
            if(raid != null) {
                log.info("Raid {} is selected", raid.getDate());
                raidPanel.setRaid(raid);
            }
        };
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JTabbedPane tabPane = (JTabbedPane) e.getSource();

        if(tabPane.getSelectedIndex()==3) {
            log.info("Raid admin tab is selected, refreshing raids");

            refreshRaids();
        }
    }

    public void refreshRaids() {
        raids = service.getRaids();
        DefaultListModel<Raid> model = new DefaultListModel<>();
        raids.forEach(model::addElement);

        list.setModel(model);
        list.setSelectedIndex(0);
    }

    public void refreshRaids(Raid raid) {
        refreshRaids();
        list.setSelectedIndex(findIndexOf(raid));
    }

    private int findIndexOf(Raid raid) {
        for(int i=0;i<raids.size();i++) {
            if(raids.get(i).getId()==raid.getId()) {
                return i;
            }
        }
        return 0;
    }
}
