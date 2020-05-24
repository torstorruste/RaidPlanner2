package org.superhelt.raidplanner2.client.gui.instanceAdmin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.client.gui.cellRenderers.InstanceCellRenderer;
import org.superhelt.raidplanner2.client.service.InstanceService;
import org.superhelt.raidplanner2.om.Instance;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.util.Comparator;
import java.util.List;

public class InstanceAdminPanel extends JSplitPane {

    private static final Logger log = LoggerFactory.getLogger(InstanceAdminPanel.class);

    private final InstanceService service;
    private List<Instance> instances;
    private JList<Instance> list;
    private InstancePanel instancePanel;

    public InstanceAdminPanel(InstanceService service) {
        this.service = service;

        initGui();
    }

    private void initGui() {
        DefaultListModel<Instance> model = new DefaultListModel<>();
        instances = service.getInstances();
        instances.sort(Comparator.comparing(Instance::getName));
        instances.forEach(model::addElement);

        list = new JList<>();
        list.setModel(model);
        list.setSelectedIndex(0);
        list.setCellRenderer(new InstanceCellRenderer());
        list.setDragEnabled(false);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(getSelectionListener());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        leftPanel.add(list);
        leftPanel.add(new AddInstancePanel(this, service));

        setLeftComponent(leftPanel);
        instancePanel = new InstancePanel(service, this, instances.get(0));
        setRightComponent(instancePanel);
        setEnabled(false);
    }

    private ListSelectionListener getSelectionListener() {
        return e->{
            int selectedIndex = list.getSelectedIndex();
            if(!e.getValueIsAdjusting() && selectedIndex >=0) {
                Instance instance = instances.get(selectedIndex);
                log.info("Selecting player {} (index={})", instance.getName(), selectedIndex);
                instancePanel.setInstance(instance);
            }
        };
    }

    public void refreshPanel(Instance instance) {
        log.info("Refreshing list with instance {}", instance.getName());
        instances = service.getInstances();
        instances.sort(Comparator.comparing(Instance::getName));

        int index = getIndex(instances, instance);

        DefaultListModel<Instance> model = new DefaultListModel<>();
        instances.forEach(model::addElement);
        list.setModel(model);
        list.setSelectedIndex(index);
    }

    private int getIndex(List<Instance> instances, Instance instance) {
        for(int i=0;i<instances.size();i++) {
            if(instances.get(i).getId()==instance.getId()) {
                return i;
            }
        }
        return 0;
    }
}
