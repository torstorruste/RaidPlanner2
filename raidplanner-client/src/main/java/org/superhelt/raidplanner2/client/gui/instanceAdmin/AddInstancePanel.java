package org.superhelt.raidplanner2.client.gui.instanceAdmin;

import org.superhelt.raidplanner2.client.gui.IconUtil;
import org.superhelt.raidplanner2.client.service.InstanceService;
import org.superhelt.raidplanner2.om.Instance;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class AddInstancePanel  extends JPanel {

    private final InstanceAdminPanel parent;
    private final InstanceService service;

    private JTextField nameField;

    public AddInstancePanel(InstanceAdminPanel parent, InstanceService service) {
        this.parent = parent;
        this.service = service;

        initGui();
    }

    private void initGui() {
        nameField = new JTextField(10);
        add(nameField);

        add(new JButton(getAddAction()));
    }

    private Action getAddAction() {
        return new AbstractAction(null, IconUtil.getAddIcon()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!nameField.getText().isEmpty()) {
                    Instance savedInstance = service.addInstance(new Instance(-1, nameField.getText(), new ArrayList<>()));
                    parent.refreshPanel(savedInstance);
                    nameField.setText("");
                }
            }
        };
    }
}
