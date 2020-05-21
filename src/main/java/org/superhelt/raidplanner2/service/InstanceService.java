package org.superhelt.raidplanner2.service;

import org.jvnet.hk2.annotations.Service;
import org.superhelt.raidplanner2.dao.InstanceDao;
import org.superhelt.raidplanner2.om.Instance;

import javax.inject.Inject;
import java.util.List;

@Service
public class InstanceService {

    private InstanceDao dao;

    @Inject
    public InstanceService(InstanceDao dao) {
        this.dao = dao;
    }

    public List<Instance> getInstances() {
        return dao.getInstances();
    }
}
