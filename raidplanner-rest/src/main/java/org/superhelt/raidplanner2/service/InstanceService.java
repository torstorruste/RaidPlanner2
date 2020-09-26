package org.superhelt.raidplanner2.service;

import org.jvnet.hk2.annotations.Service;
import org.superhelt.raidplanner2.ServerException;
import org.superhelt.raidplanner2.dao.InstanceDao;
import org.superhelt.raidplanner2.om.Boss;
import org.superhelt.raidplanner2.om.Instance;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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

    public Instance getInstance(int id) {
        return dao.get(id).orElseThrow(()->new ServerException(404, "Instance with id %d does not exist", id));
    }

    public Instance updateInstance(Instance instance) {
        dao.update(instance);
        return instance;
    }

    public Instance addInstance(Instance instance) {
        if(dao.get().stream().anyMatch(p->p.getName().equals(instance.getName()))) {
            throw new ServerException("An instance with the name %s already exists", instance.getName());
        }

        Instance instanceToSave = new Instance(findInstanceId(), instance.getName(), new ArrayList<>());
        dao.add(instanceToSave);
        return instanceToSave;
    }

    public void deleteInstance(int id) {
        if(dao.get().stream().noneMatch(p->p.getId()==id)) {
            throw new ServerException(404, "No instance with id %d exists", id);
        }

        dao.delete(id);
    }

    public Boss addBoss(Instance instance, Boss boss) {
        if (instance.getBosses().stream().anyMatch(b -> b.getName().equals(boss.getName()))) {
            throw new ServerException("Instance %s already contains a boss with name %s", instance.getName(), boss.getName());
        }

        Boss bossToAdd = new Boss(findBossId(), boss.getName());

        instance.getBosses().add(bossToAdd);

        dao.update(instance);
        return bossToAdd;
    }

    public void updateBoss(Instance instance, Boss boss) {
        if (instance.getBosses().stream().noneMatch(b -> b.getId() == boss.getId())) {
            throw new ServerException("Instance %s does not contain a boss with id %d", instance.getName(), boss.getId());
        }

        instance.getBosses().removeIf(b -> b.getId() == boss.getId());
        instance.getBosses().add(boss);

        dao.update(instance);
    }

    public void deleteBoss(Instance instance, int bossId) {
        if (instance.getBosses().stream().noneMatch(b -> b.getId() == bossId)) {
            throw new ServerException("Instance %s does not contain a boss with id %d", instance.getName(), bossId);
        }

        instance.getBosses().removeIf(b -> b.getId() == bossId);

        dao.update(instance);
    }

    private int findInstanceId() {
        return dao.get().stream().mapToInt(Instance::getId).max().orElse(0)+1;
    }

    private int findBossId() {
        return dao.get().stream().flatMap(i->i.getBosses().stream()).mapToInt(Boss::getId).max().orElse(0)+1;
    }
}
