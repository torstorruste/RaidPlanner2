package org.superhelt.raidplanner2.service;

import org.jvnet.hk2.annotations.Service;
import org.superhelt.raidplanner2.dao.InstanceDao;
import org.superhelt.raidplanner2.om.Boss;
import org.superhelt.raidplanner2.om.Instance;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Service
public class InstanceService {

    private final InstanceDao dao;

    @Inject
    public InstanceService(InstanceDao dao) {
        this.dao = dao;
    }

    public List<Instance> getInstances() {
        return dao.get();
    }

    public Optional<Instance> getInstance(int id) {
        return dao.get(id);
    }

    public Instance addBoss(Instance instance, Boss boss) {
        if(instance.getBosses().stream().anyMatch(b->b.getId()==boss.getId())) {
            throw new ServiceException("Instance %s already containts a boss with id %d", instance.getName(), boss.getId());
        }

        instance.getBosses().add(boss);

        dao.update(instance);
        return instance;
    }

    public void updateBoss(Instance instance, Boss boss) {
        if(instance.getBosses().stream().noneMatch(b->b.getId()==boss.getId())) {
            throw new ServiceException("Instance %s does not contain a boss with id %d", instance.getName(), boss.getId());
        }

        instance.getBosses().removeIf(b->b.getId()==boss.getId());
        instance.getBosses().add(boss);

        dao.update(instance);
    }
}
