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
        if (instance.getBosses().stream().anyMatch(b -> b.getName().equals(boss.getName()))) {
            throw new ServiceException("Instance %s already contains a boss with name %s", instance.getName(), boss.getName());
        }

        Boss bossToAdd = new Boss(findId(), boss.getName());

        instance.getBosses().add(bossToAdd);

        dao.update(instance);
        return instance;
    }

    private int findId() {
        return dao.get().stream().flatMap(i->i.getBosses().stream()).mapToInt(Boss::getId).max().orElse(0)+1;
    }

    public void updateBoss(Instance instance, Boss boss) {
        if (instance.getBosses().stream().noneMatch(b -> b.getId() == boss.getId())) {
            throw new ServiceException("Instance %s does not contain a boss with id %d", instance.getName(), boss.getId());
        }

        instance.getBosses().removeIf(b -> b.getId() == boss.getId());
        instance.getBosses().add(boss);

        dao.update(instance);
    }

    public void deleteBoss(Instance instance, int bossId) {
        if (instance.getBosses().stream().noneMatch(b -> b.getId() == bossId)) {
            throw new ServiceException("Instance %s does not contain a boss with id %d", instance.getName(), bossId);
        }

        instance.getBosses().removeIf(b -> b.getId() == bossId);

        dao.update(instance);
    }
}
