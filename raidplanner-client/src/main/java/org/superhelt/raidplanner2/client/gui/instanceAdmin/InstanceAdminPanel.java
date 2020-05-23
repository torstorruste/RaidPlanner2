package org.superhelt.raidplanner2.client.gui.instanceAdmin;

import org.superhelt.raidplanner2.client.service.InstanceService;
import org.superhelt.raidplanner2.om.Instance;

import javax.swing.*;
import java.util.Comparator;
import java.util.List;

public class InstanceAdminPanel extends JSplitPane {

    private final InstanceService service;
    private List<Instance> instances;

    public InstanceAdminPanel(InstanceService service) {
        this.service = service;

        initGui();
    }

    private void initGui() {
        JPanel leftPanel = new JPanel();
        DefaultListModel<Instance> model = new DefaultListModel<>();
        instances = service.getInstances();
        instances.sort(Comparator.comparing(Instance::getName));
        instances.forEach(model::addElement);

        JList<Instance> list = new JList<>();
        list.setModel(model);
        list.setSelectedIndex(0);
        list.setCellRenderer(new InstanceCellRenderer());
        list.setDragEnabled(false);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        leftPanel.add(list);

        setLeftComponent(leftPanel);
        setRightComponent(new InstancePanel(service, instances.get(0)));
        setEnabled(false);
    }
}
