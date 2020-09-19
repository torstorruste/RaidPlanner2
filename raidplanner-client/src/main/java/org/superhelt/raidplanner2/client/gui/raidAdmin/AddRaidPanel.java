package org.superhelt.raidplanner2.client.gui.raidAdmin;

import org.jdatepicker.DateModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.superhelt.raidplanner2.client.gui.IconUtil;
import org.superhelt.raidplanner2.client.service.RaidService;
import org.superhelt.raidplanner2.om.Raid;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;

public class AddRaidPanel extends JPanel {

    private final RaidService service;
    private final RaidAdminPanel parent;
    private JDatePickerImpl datePicker;

    public AddRaidPanel(RaidAdminPanel parent, RaidService service) {
        this.service = service;
        this.parent = parent;

        initGui();
    }

    private void initGui() {
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        add(datePicker);
        add(new JButton(getAddRaidAction()));
    }

    private Action getAddRaidAction() {
        return new AbstractAction(null, IconUtil.getIcon("add")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                DateModel<?> model = datePicker.getModel();
                LocalDate date = LocalDate.of(model.getYear(), model.getMonth()+1, model.getDay());

                Raid raid = new Raid(-1, date, new ArrayList<>(), new ArrayList<>());
                Raid savedRaid = service.addRaid(raid);

                parent.refreshRaids(savedRaid);
            }
        };
    }


}
