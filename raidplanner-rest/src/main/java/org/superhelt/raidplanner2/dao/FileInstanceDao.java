package org.superhelt.raidplanner2.dao;

import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.om.Boss;
import org.superhelt.raidplanner2.om.Instance;

import javax.inject.Singleton;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Singleton
public class FileInstanceDao implements InstanceDao {

    private static final Logger log = LoggerFactory.getLogger(FileInstanceDao.class);

    private static final Path jsonFile = Paths.get("instances.json");
    private static final List<Instance> instances = new ArrayList<>();

    static {
        try {
            List<Instance> storedInstances = FileWriter.readFromFile(jsonFile, Instance[].class);
            instances.addAll(storedInstances);
            sortInstances();
        } catch (IOException e) {
            log.error("Unable to read instances from {}", jsonFile);
        }
    }

    public List<Instance> get() {
        return instances;
    }

    public Optional<Instance> get(int id) {
        return instances.stream().filter(i->i.getId()==id).findFirst();
    }

    @Override
    public void add(Instance instance) {
        instances.add(instance);
        sortInstances();
        FileWriter.writeToFile(jsonFile, instances);
    }

    public void update(Instance instance) {
        instances.removeIf(i->i.getId()==instance.getId());
        instances.add(instance);
        sortInstances();
        FileWriter.writeToFile(jsonFile, instances);
    }

    @Override
    public void delete(int id) {
        instances.removeIf(i->i.getId()==id);
        sortInstances();
        FileWriter.writeToFile(jsonFile, instances);
    }

    private static void sortInstances() {
        instances.sort(Comparator.comparing(Instance::getName));
        instances.forEach(i->i.getBosses().sort(Comparator.comparing(Boss::getId)));
    }
}
