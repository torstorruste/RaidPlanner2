package org.superhelt.raidplanner2.dao;

import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.om.Raid;

import javax.inject.Singleton;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Singleton
public class FileRaidDao implements RaidDao {

    private static final Logger log = LoggerFactory.getLogger(FileRaidDao.class);

    private final static Path jsonFile = Paths.get("raids.json");
    private final static List<Raid> raids = new ArrayList<>();

    static {
        try {
            List<Raid> storedRaids = FileWriter.readFromFile(jsonFile, Raid[].class);
            raids.addAll(storedRaids);
            sortRaids();
        } catch (Exception e) {
            log.error("Unable to read {}", jsonFile, e);
        }
    }

    private static void sortRaids() {
        raids.sort(Comparator.comparing(Raid::getDate));
    }

    @Override
    public List<Raid> getAll() {
        return raids;
    }

    @Override
    public Optional<Raid> get(int id) {
        return raids.stream().filter(p->p.getId()==id).findFirst();
    }

    @Override
    public void add(Raid raid) {
        raids.add(raid);
        sortRaids();
        FileWriter.writeToFile(jsonFile, raids);
    }

    @Override
    public void update(Raid raid) {
        raids.removeIf(r->r.getId()==raid.getId());
        raids.add(raid);
        sortRaids();
        FileWriter.writeToFile(jsonFile, raids);

    }
}
