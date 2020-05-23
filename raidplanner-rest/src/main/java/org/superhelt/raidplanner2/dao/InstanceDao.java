package org.superhelt.raidplanner2.dao;

import org.jvnet.hk2.annotations.Contract;
import org.superhelt.raidplanner2.om.Instance;

import java.util.List;
import java.util.Optional;

@Contract
public interface InstanceDao {

    List<Instance> get();

    Optional<Instance> get(int id);

    void update(Instance instance);

    void add(Instance instance);
}