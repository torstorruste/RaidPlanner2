package org.superhelt.raidplanner2.dao;

import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.om.Instance;

import javax.inject.Singleton;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Singleton
public class FileInstanceDao implements InstanceDao {

    private static final Logger log = LoggerFactory.getLogger(FileInstanceDao.class);

    private static Path jsonFile = Paths.get("instances.json");
    private static List<Instance> instances = new ArrayList<>();


    static {
        try {
            List<Instance> storedInstances = FileWriter.readFromFile(jsonFile, Instance[].class);
            instances.addAll(storedInstances);
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

    public void update(Instance instance) {
        instances.removeIf(i->i.getId()==instance.getId());
        instances.add(instance);
        FileWriter.writeToFile(jsonFile, instances);
    }
}
